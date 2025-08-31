package com.manjosh.labs.backend.domain;

import com.manjosh.labs.backend.domain.expense.Expense;
import com.manjosh.labs.backend.domain.expense.ExpenseService;
import com.manjosh.labs.backend.domain.profile.Profile;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final ProfileService profileService;
    private final EmailService emailService;
    private final ExpenseService expenseService;

    @Value("${gui.url}")
    private String guiUrl;

    @Scheduled(cron = "0 0 22 * * *", zone = "IST")
    public void sendNotification() {
        log.info("Sending notification job started");
        final List<Profile> allProfiles = profileService.getAllProfiles();
        allProfiles.forEach(profile -> {
            try {
                String subject = "Xpensive: Add today's expenses";
                String emailBody = buildNotificationEmailBody(profile.fullName());
                log.info("Sending notification to profile {}: {}", profile.id(), profile.email());
                emailService.sendEmail(profile.email(), subject, emailBody);
            } catch (Exception e) {
                log.error("Error sending notification to profile {}: {}", profile.id(), e.getMessage());
            }
        });
    }

    @Scheduled(cron = "0 0 23 * * *", zone = "IST")
    public void sendDailyExpenseSummary() {
        log.info("Sending daily expense summary job started");
        List<Profile> allProfiles = profileService.getAllProfiles();
        LocalDate today = LocalDate.now();

        for (Profile profile : allProfiles) {
            try {
                List<Expense> expensesForDate = expenseService.getExpensesForDate(today, profile.id());
                if (expensesForDate.isEmpty()) {
                    continue;
                }

                // Build HTML table
                StringBuilder table = new StringBuilder();
                table.append(
                        """
                    <div style="margin: 25px 0;">
                        <h3 style="color: #f8fafc; margin: 0 0 20px 0; font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; font-size: 1.5rem; font-weight: 600; letter-spacing: -0.01em;">Your Expenses for %s</h3>
                        <table style="width: 100%; border-collapse: separate; border-spacing: 0; margin-top: 10px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #1e1e2d; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 15px rgba(0, 0, 0, 0.2);">
                            <thead>
                                <tr style="background: linear-gradient(135deg, #4c1d95 0%%, #86198f 100%%);">
                                    <th style="padding: 14px 16px; text-align: left; color: #f8fafc; font-weight: 500; border: none; border-bottom: 2px solid #5b21b6;">Description</th>
                                    <th style="padding: 14px 16px; text-align: right; color: #f8fafc; font-weight: 500; border: none; border-bottom: 2px solid #5b21b6;">Amount</th>
                                    <th style="padding: 14px 16px; text-align: left; color: #f8fafc; font-weight: 500; border: none; border-bottom: 2px solid #5b21b6;">Category</th>

                                </tr>
                            </thead>
                            <tbody style="background-color: #1e1e2d;">"""
                                .formatted(today.toString()));

                BigDecimal total = BigDecimal.ZERO;
                for (Expense expense : expensesForDate) {
                    table.append(
                            """
                        <tr style="transition: background-color 0.2s ease;" onmouseover="this.style.backgroundColor='#2a2a3a'" onmouseout="this.style.backgroundColor='#1e1e2d'">
                            <td style="padding: 14px 16px; color: #e2e8f0; border-bottom: 1px solid #2a2a3a;">%s</td>
                            <td style="padding: 14px 16px; text-align: right; color: #a5f3fc; font-weight: 500; border-bottom: 1px solid #2a2a3a;">$%s</td>
                            <td style="padding: 14px 16px; color: #cbd5e1; border-bottom: 1px solid #2a2a3a;">%s</td>

                        </tr>
                        """
                                    .formatted(
                                            escapeHtml(expense.description()),
                                            expense.amount() != null ? String.format("%.2f", expense.amount()) : "0.00",
                                            expense.categoryName()));
                    total = total.add(expense.amount() != null ? expense.amount() : BigDecimal.ZERO);
                }

                // Add total row
                table.append(
                        """
                    <tr style="background-color: #1e1b4b;">
                        <td colspan="3" style="padding: 12px; text-align: right; font-weight: bold; border: 1px solid #4c1d95; color: #e9d5ff;">Total:</td>
                        <td style="padding: 12px; text-align: right; font-weight: bold; border: 1px solid #4c1d95; color: #e9d5ff;">$%.2f</td>
                    </tr>
                    </tbody>
                    </table>
                    </div>
                    """
                                .formatted(total));

                // Build the complete email body
                String emailBody =
                        """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <style>
                            body {
                                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                                line-height: 1.6;
                                color: #e2e8f0;
                                max-width: 800px;
                                margin: 0 auto;
                                background-color: #0f172a;
                                padding: 20px;
                            }
                            .container {
                                background-color: #1e293b;
                                border-radius: 8px;
                                padding: 0;
                                overflow: hidden;
                            }
                            .header {
                                background: linear-gradient(135deg, #4c1d95 0%, #86198f 100%);
                                color: #f8fafc;
                                padding: 25px 30px;
                                text-align: center;
                            }
                            .content {
                                padding: 30px;
                                color: #e2e8f0;
                            }
                            .footer {
                                padding: 20px;
                                text-align: center;
                                color: #94a3b8;
                                font-size: 12px;
                                border-top: 1px solid #334155;
                            }
                            h1, h2, h3 {
                                color: #f8fafc;
                                margin-top: 0;
                            }
                            a {
                                color: #a855f7;
                                text-decoration: none;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <div class="header">
                                <h1>Xpensive Daily Summary</h1>
                                <p>Here's your expense summary for %s</p>
                            </div>
                            <div class="content">
                                %s
                                <p style="margin-top: 25px;">
                                    <a href="%s" style="display: inline-block; background: linear-gradient(135deg, #7e22ce 0%%, #d946ef 100%%); color: #f8fafc; padding: 12px 25px; border-radius: 6px; text-decoration: none; font-weight: 600; margin-top: 20px;">View Full Dashboard</a>
                                </p>
                            </div>
                            <div class="footer">
                                2025 Xpensive. All rights reserved.<br>
                                <a href="%s/privacy-policy">Privacy Policy</a> |
                                <a href="%s/contact">Contact Us</a>
                            </div>
                        </div>
                    </body>
                    </html>
                    """
                                .formatted(today.toString(), table.toString(), guiUrl, guiUrl, guiUrl);

                // Send the email
                String subject = String.format("Xpense Summary - %s", today);
                emailService.sendEmail(profile.email(), subject, emailBody);
                log.info("Daily expense summary sent to profile {}", profile.id());

            } catch (Exception e) {
                log.error("Error sending daily expense summary to profile {}: {}", profile.id(), e.getMessage(), e);
            }
        }
    }

    private String escapeHtml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    protected String buildNotificationEmailBody(String userName) {
        return String.format(
                """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        line-height: 1.6;
                        color: #e2e8f0;
                        max-width: 600px;
                        margin: 0 auto;
                        background-color: #0f172a;
                    }
                    .header {
                        background: linear-gradient(135deg, #4c1d95 0%%, #86198f 100%%);
                        color: #f8fafc;
                        padding: 30px 20px;
                        text-align: center;
                        border-radius: 8px 8px 0 0;
                        box-shadow: 0 4px 6px -1px rgba(124, 58, 237, 0.3);
                    }
                    .content {
                        padding: 30px;
                        background-color: #1e293b;
                        border-left: 1px solid #334155;
                        border-right: 1px solid #334155;
                        color: #e2e8f0;
                    }
                    .button {
                        display: inline-block;
                        padding: 14px 28px;
                        background: linear-gradient(135deg, #7e22ce 0%%, #d946ef 100%%);
                        color: #f8fafc;
                        text-decoration: none;
                        border-radius: 6px;
                        margin: 20px 0;
                        font-weight: 600;
                        letter-spacing: 0.5px;
                        box-shadow: 0 4px 6px -1px rgba(124, 58, 237, 0.3);
                        transition: all 0.2s ease;
                    }
                    .button:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 10px 15px -3px rgba(124, 58, 237, 0.4);
                    }
                    .footer {
                        margin-top: 0;
                        font-size: 12px;
                        color: #94a3b8;
                        text-align: center;
                        padding: 20px;
                        background-color: #1e293b;
                        border-radius: 0 0 8px 8px;
                        border: 1px solid #334155;
                        border-top: none;
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <h1>Finance Tracker</h1>
                </div>
                <div class="content">
                    <h2>Hello, %s!</h2>
                    <p style="color: #cbd5e1;">Here's your daily financial summary. Stay on top of your finances by checking your dashboard for the latest updates.</p>

                    <div style="background-color: #2e1065; border-left: 4px solid #a855f7; padding: 16px; margin: 20px 0; border-radius: 6px;">
                        <p style="margin: 0; color: #e9d5ff; font-size: 15px; line-height: 1.5;">
                            <strong>Don't forget to log today's expenses!</strong> Keeping your records up to date helps you stay on top of your budget and financial goals.
                        </p>
                    </div>

                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" class="button">View My Dashboard</a>
                    </div>

                    <p style="color: #cbd5e1; margin: 25px 0 0 0;">If the button above doesn't work, you can copy and paste this link into your browser:</p>
                    <p style="word-break: break-all; background-color: #1e293b; padding: 12px; border-radius: 6px; border: 1px solid #4c1d95; margin: 10px 0 0 0;">
                        <a href="%s" style="color: #a855f7; text-decoration: none; font-weight: 500;">%s</a>
                    </p>

                    <p style="color: #cbd5e1;">Thank you for using Finance Tracker!</p>
                    <p style="color: #cbd5e1;">Best regards,<br>The Finance Tracker Team</p>
                </div>
                <div class="footer">
                    2025 Finance Tracker. All rights reserved.<br>
                    <a href="%s/privacy-policy" style="color: #c084fc; text-decoration: none;">Privacy Policy</a> |
                    <a href="%s/contact" style="color: #c084fc; text-decoration: none;">Contact Us</a>
                </div>
            </body>
            </html>
            """,
                userName, guiUrl, guiUrl, guiUrl, guiUrl, guiUrl);
    }
}
