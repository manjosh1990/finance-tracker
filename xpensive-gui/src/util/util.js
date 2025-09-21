import moment from "moment";

export const addThousandSeparator = (num) => {
  // Convert to string
  const numStr = num.toString();
  
  // Split number by decimal point
  const parts = numStr.split('.');
  
  // Format the integer part according to Indian numbering system
  let integerPart = parts[0];
  let formattedInteger = '';
  
  // Handle the first digits (up to last 3 digits differently)
  const firstDigits = integerPart.length % 2 === 0 ? integerPart.slice(0, 2) : integerPart.slice(0, 1);
  const remainingDigits = integerPart.length % 2 === 0 ? integerPart.slice(2) : integerPart.slice(1);
  
  if (integerPart.length <= 3) {
    // For small numbers, no special formatting needed
    formattedInteger = integerPart;
  } else {
    // Apply the Indian number system (first 3 digits, then groups of 2)
    const lastThreeDigits = integerPart.slice(-3);
    const otherDigits = integerPart.slice(0, -3);
    
    if (otherDigits) {
      // Add commas after every 2 digits from right to left
      formattedInteger = otherDigits.replace(/(\d)(?=(\d{2})+(?!\d))/g, '$1,') + ',' + lastThreeDigits;
    } else {
      formattedInteger = lastThreeDigits;
    }
  }
  
  // Add decimal part if exists
  return parts.length > 1 ? formattedInteger + '.' + parts[1] : formattedInteger;
};

/**
 * Formats a number according to the Indian number system (lakhs, crores)
 * @param {number} num - The number to format
 * @returns {string} - Formatted string with commas as per Indian number system
 */
export const formatIndianNumber = (num) => {
  if (!num && num !== 0) return '';
  
  const numStr = num.toString();
  let result = '';
  
  // Handle decimal part if exists
  const parts = numStr.split('.');
  const integerPart = parts[0];
  const decimalPart = parts.length > 1 ? '.' + parts[1] : '';
  
  // Apply Indian number system formatting (1,23,456.78)
  // First add a comma after the first 3 digits from the right, then after every 2 digits
  const lastThree = integerPart.length > 3 ? integerPart.substring(integerPart.length - 3) : integerPart;
  const remaining = integerPart.length > 3 ? integerPart.substring(0, integerPart.length - 3) : '';
  
  if (remaining !== '') {
    result = remaining.replace(/\B(?=(\d{2})+(?!\d))/g, ',') + ',' + lastThree;
  } else {
    result = lastThree;
  }
  
  return result + decimalPart;
};

export const prepareIncomeLineChartData = (data) => {
    const groupedByDate = data.reduce((acc, item) => {
        const dateKey = item.transactionDate // Assuming 'date' is in 'YYYY-MM-DD' format

        if (!acc[dateKey]) {
            acc[dateKey] = {
                date: dateKey, // Keep the raw date for sorting if needed
                totalAmount: 0,
                items: [], // Array to store original items for this date
            };
        }

        acc[dateKey].totalAmount += item.amount;
        acc[dateKey].items.push(item); // Add the original item to the array
        return acc;
    }, {});

    // Convert the grouped object back to an array
    let chartData = Object.values(groupedByDate);

    // Sort the data by date in ascending order for the line chart
    chartData.sort((a, b) => new Date(a.date) - new Date(b.date));

    // Format the date for the X-axis label
    chartData = chartData.map((dataPoint) => ({
        ...dataPoint,
        month: moment(dataPoint.date).format('Do MMM'), // Formatted date for XAxis
    }));
    return chartData;
};