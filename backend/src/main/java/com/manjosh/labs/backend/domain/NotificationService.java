package com.manjosh.labs.backend.domain;

import com.manjosh.labs.backend.domain.expense.ExpenseService;
import com.manjosh.labs.backend.domain.profile.Profile;
import com.manjosh.labs.backend.domain.profile.ProfileService;
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
                String subject = "Your Daily Financial Summary";
                String emailBody = buildEmailBody(profile.fullName());
                log.info("Sending notification to profile {}: {}", profile.id(), profile.email());
                emailService.sendEmail(profile.email(), subject, emailBody);
            } catch (Exception e) {
                log.error("Error sending notification to profile {}: {}", profile.id(), e.getMessage());
            }
        });
    }

    protected String buildEmailBody(String userName) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; }
                    .header { background-color: #4a90e2; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }
                    .content { padding: 20px; background-color: #f9f9f9; }
                    .button {
                        display: inline-block;
                        padding: 12px 24px;
                        background-color: #4a90e2;
                        color: white;
                        text-decoration: none;
                        border-radius: 4px;
                        margin: 20px 0;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 12px;
                        color: #777;
                        text-align: center;
                        padding: 10px;
                        border-top: 1px solid #eee;
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <h1>Finance Tracker</h1>
                </div>
                <div class="content">
                    <h2>Hello, %s!</h2>
                    <p>Here's your daily financial summary. Stay on top of your finances by checking your dashboard for the latest updates.</p>

                    <div style="background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 12px; margin: 15px 0; border-radius: 4px;">
                        <p style="margin: 0; color: #856404;">
                            <strong>Don't forget to log today's expenses!</strong> Keeping your records up to date helps you stay on top of your budget and financial goals.
                        </p>
                    </div>

                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" class="button">View My Dashboard</a>
                    </div>

                    <p>If the button above doesn't work, you can copy and paste this link into your browser:</p>
                    <p><a href="%s">%s</a></p>

                    <p>Thank you for using Finance Tracker!</p>
                    <p>Best regards,<br>The Finance Tracker Team</p>
                </div>
                <div class="footer">
                    2025 Finance Tracker. All rights reserved.<br>
                    <a href="%s/privacy-policy" style="color: #4a90e2;">Privacy Policy</a> |
                    <a href="%s/contact" style="color: #4a90e2;">Contact Us</a>
                </div>
            </body>
            </html>
            """
                .formatted(userName, guiUrl, guiUrl, guiUrl, guiUrl, guiUrl);
    }
}
