package org.example;


public class App 
{
    public static void main( String[] args )
    {

        if( args.length >= 2) {
            System.out.println(args[0] + " " + args[1]);
            GtAwsDirectory awsDirectory = new GtAwsDirectory(args[0], args[1]);
            awsDirectory.executeCommand();
        }else{
            System.out.println("Please set param: \n\t 1: host ip or name \n\t 2: folder name  ");
        }
    }
}
