package org.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println(args[0] + " " + args[1]);
     /*   GtAwsDirectory awsDirectory = new GtAwsDirectory("18.203.171.233", "home");
        awsDirectory.executeCommand();
*/
        if( args.length >= 2) {
            GtAwsDirectory awsDirectory = new GtAwsDirectory(args[0], args[1]);
            awsDirectory.executeCommand();
        }else{
            System.out.println("Please set param: \n\t 1: host ip or name \n\t 2: folder name  ");
        }
    }
}
