import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

// Calculates the price an item needs to sell at to be profitable
// Lynnwood, Washington sales tax is 10.6% - $13.17 shipping cost
// Ouachita Parish, Louisiana sales tax is 12.95% - $7.58 shipping
// Lynnwood, Washington is more expensive until ~$200 cost

class ebayCalculator {

    public static void main(String args[]){
        double salesTaxRate = 0.106; // Lynnwood, Washington sales tax is 10.6%
        double shippingOneLb = 10.06; // Lynnwood, Washington 1 lb 8x6x5 box is $10.07
        double shippingTwoLb = 13.17; // Lynnwood, Washington 2 lb 8x6x5 box is $10.07
        double shippingCard = 0.69; // Flat fee for 1 oz shipping with ebay standard envelope
        double finalValueFeePercentage = 0.1235; // eBay final value fee on collectibles is 12.35%
        Scanner scan = new Scanner(System.in); // Accept input from user for which shipping method
        boolean continueLoop = true;
        int userSelection = 0;

        while (continueLoop) {
            System.out.println("Please enter the shipping method you will be using:\n1. 1lb Box\n2. 2lb Box\n3. eBay Standard Envelope\n9. Quit");
            userSelection = scan.nextInt();
            if (userSelection == 1) {
                calculateMinSalePrice(shippingOneLb, finalValueFeePercentage, salesTaxRate);
            }
            else if (userSelection == 2) {
                calculateMinSalePrice(shippingTwoLb, finalValueFeePercentage, salesTaxRate);
            }
            else if (userSelection == 3) {
                calculateMinSalePrice(shippingCard, finalValueFeePercentage, salesTaxRate);
            }
            else if (userSelection == 9) {
                System.out.println("Ending Program!");;
                continueLoop = false;
            }
        }
    }

    public static void calculateMinSalePrice(double shippingCost, double fvfp, double salesTaxRate){
        //int subTotal = 0; // Increment the subtotal by $1
        double minimumSalePrice = 0.0; // Stores the minimumSalePrice to break even based on the sale price
        double orderTotal = 0.0; // Stores the order total before fees
        double orderFees = 0.0; // Stores the total fees for the order
        double extraPenny = 0.01; // Rounds up the total by 1 penny to provide buffer for eBay rounding
        double finalValueFeeFixed = 0.0; // Stores the fixed fvf. $0.40 if order total is greater thean $10. Otherwise its $0.30
        double salesTaxValue = 0.0; // Stores the calculated sales tax

        try{
            String outputFilePath = "C:/Users/jacks/OneDrive/Documents/Downloads/eBaySaleCalculations.txt";
            File myFile = new File(outputFilePath); // Create a file to export the values


            if (myFile.createNewFile()) {
                Writer myWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath)));
                myWriter.write("SubTotal \t Max Cost\n");
            
                for(double subTotal = 0.99; subTotal < 100; subTotal+=0.01){ // Calculate for every subtotal incement of $1
                    salesTaxValue = ((subTotal + shippingCost) * salesTaxRate); // Calculates the sales tax
                    orderTotal = (subTotal + shippingCost + salesTaxValue);
                    if (orderTotal > 10.00) {
                        finalValueFeeFixed = 0.40; // $0.40 fixed final value fee added
                    }
                    else{
                        finalValueFeeFixed = 0.30; // $0.30 fixed final value fee added
                    }
                    orderFees = ((orderTotal * fvfp)  + extraPenny + finalValueFeeFixed);
                    minimumSalePrice = (Math.round((orderTotal - orderFees - shippingCost - salesTaxValue)*100.0))/100.0;
                    System.out.println("SubTotal: $" + subTotal + " - Max Cost: $" + minimumSalePrice);
                    myWriter.write(subTotal + "\t" + minimumSalePrice + "\n");
                }
                myWriter.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}