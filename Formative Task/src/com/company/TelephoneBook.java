package com.company;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


public class TelephoneBook {
    //basic declarations of various important global variables used throughout the various methods
    private ArrayList<Contact> Book; //base arraylist of contacts
    private int Entries; //size of current contact arraylist
    private String textFileName; //text file name used throughout the program


    TelephoneBook(String fileName){ //Constructor for when a telephone book object is made, sets up the arraylist of contacts and sets up textFileName
        Book= new ArrayList<>();
        textFileName=fileName;
        //connects the file if it is found and callcs the readFile method
        try {
            FileReader fr=new FileReader(textFileName);
            readFile(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Not Found, exiting system");
            System.exit(0);
        }
    }

    public String getDetails(int index,boolean choice){ //returns the details, whether it be name or number depending on the choice boolean of the given indexed element
        String giveBack;
        if(choice){
            giveBack=Book.get(index).getName();
        }
        else{
            giveBack=Book.get(index).getTelephoneNumber();
        }

        return giveBack;
    }
    public int getEntries() {
        return Entries;
    }

    public int getInput(int max,boolean include){ //Function used to check for input numbers out of range of valid input when collecting input, used in various situations.
        // use of include boolean decides whether 0 is included or excluded in the check for input, included in any use of index selection but excluded in any use of menu options.
        Scanner sc= new Scanner(System.in);
        int options=sc.nextInt();
        boolean check;
        if(include==true){
            check=options > -1 && options <= max;
        }
        else {
            check=options > 0 && options <= max;
        }
        while (!(check)){
            System.out.println("Please enter a valid response, above 0 and below or equal to "+ max+": \n");
            options= sc.nextInt();
        }
        return options;
    }

    public void addContact(){ // used to add any new contacts to the system and make sure the user is sure of their new contact's information, increases entries if operation is successful
        boolean sure;
        Scanner sc=new Scanner(System.in);
        do{
        System.out.println("Please enter the name of this contact: ");
        String newName = sc.nextLine();
        System.out.println("Please enter the number of this contact: ");
        String number = sc.nextLine();
        System.out.println("Are you sure you mean " + newName + " whose number is " + number + "? \n1.Yes \n2.No \n");
        if (getInput(2,false) == 1) {
            Book.add(new Contact(newName,number));
            sure = true;
        } else {
            sure = false;
        }
        } while (!sure);
        Entries++;
        sortList();
    }

    public int searchContact(String name){ //Function used to linear search through all possible contacts, returns -1 if not found, otherwise returns index value, if multiple contacts are found as the contain method is used
        // then it calls the find the right one method found below this one which narrows down the search according to the user's choice
        ArrayList<Integer> provided= new ArrayList<>();
        ArrayList<Integer> indices= new ArrayList<>();
        int last;
        String nameLC=name.toLowerCase();
        for (int i=0;i<Entries;i++){
            String nameLC2=Book.get(i).getName().toLowerCase();
            if(nameLC2.contains(nameLC)){
                provided.add(i);
            }
        }
        if(provided.size()>1){
            last=findRightOne(provided);
        }
        else if(provided.size()==1){
            last=provided.get(0);
        }
        else{
            last =-1;
        }
        return last;
    }

    private int findRightOne(ArrayList<Integer> use){ //Function to find the right search value among many different variants containing the same search, user chooses which one they want specifically
        // this choice is returned to the search method and then back to the main use of the search function
        int last=-1;
        int options;
        boolean sure;
        do {
            System.out.println("There are multiple options available under that search name, please return which one you mean from the options below:");
            for (int i = 0; i < use.size(); i++) {
                System.out.println((i + 1) + ". " + Book.get(use.get(i)).getName());
            }
            options = getInput(use.size(),false);
            System.out.println("Are you sure you mean " + Book.get(use.get(options - 1)).getName() + "? \n1.Yes \n2.No \n");
            if(getInput(2,false)==1) {
                sure = true;
                last = use.get(options-1);
            }
            else {
                sure=false;
            }
        }while(!sure);

        return last;
    }

    public void deleteContact(int index){ // displays details of contacts about to be deleted and then confirms the deletion process, decreases entries if operation is successful
        System.out.println("You are about to delete the contact under the name: " +Book.get(index).getName() +", they have the number "+Book.get(index).getTelephoneNumber()+". ");
        System.out.println("Are you sure about this? \n1.Yes\n2.No \n");
        if(getInput(2,false)==1){
            Book.remove(index);
            System.out.println("The contact was successfully deleted.");
            Entries--;
        }
        else{
            System.out.println("Alright, I guess we won't erase " +Book.get(index).getName() +" from the matrix :)" );
        }
        sortList();
    }

    public void editContact(int index){// editing contacts using an index number passed to the function, displays current details and how they will be changed before doing so.
        Scanner sc=new Scanner(System.in);
        String newName,newNumber,oldName=Book.get(index).getName(),oldNumber=Book.get(index).getTelephoneNumber();
        boolean sure;
        do {
            System.out.println("What would you like to change the name to? \n The current one is " + oldName + ": \n");
            newName = sc.nextLine();
            System.out.println("What would you like to change the number to? \n The current one is " + oldNumber + ": \n");
            newNumber = sc.nextLine();
            System.out.println("This will change the name from "+ oldName +" to " + newName + " and the number from " + oldNumber + " to " + newNumber + ". \nAre you sure about this? \n1.Yes \n2.No,let me change the details again \n3.No, keep it all the same \n");
            int input=getInput(3,false);
            if (input == 1) {
                Book.get(index).setName(newName);
                Book.get(index).setTelephoneNumber(newNumber);
                System.out.println("Contact changed successfully :) \n");
                sure = true;
            } else if(input== 2) {
                sure = false;
            }
            else{
                System.out.println("Alright, I guess we won't change their details for now. \n");
                sure=true;
            }
        }while(!sure);
        sortList();
    }




    private void readFile(FileReader fr){ //Function used to read to read the text file in and add it to the array list, reads the number of entries first to know how many lines to read
        BufferedReader br=new BufferedReader(fr);
        try {
            String noOfEntries=br.readLine();
            if(noOfEntries==null){
                Entries=0;
            }
            else {
                Entries = Integer.parseInt(noOfEntries);
                String line;
                for (int i = 0; i < Entries; i++) {
                    line = br.readLine();
                    String[] temp = line.split(",");
                    Book.add(new Contact(temp[0], temp[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(){ // figures out the size of the current arraylist of contacts, sorts the list and writes the number of entries first to the text file before then writing out all the current contacts
        // it uses the format "name,number" to store contacts and allows for easy reading as well.
        Book.trimToSize();
        sortList();
        try {
            FileWriter fw= new FileWriter(textFileName);
            BufferedWriter bw=new BufferedWriter(fw);

            bw.write(Entries + "\n");
            sortList();

            if(Entries!=0) {
                for (int i = 0; i < Entries; i++) {
                    bw.write(Book.get(i).getName() + "," + Book.get(i).getTelephoneNumber() + "\n" );
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //algorithm used in various places, sorts using bubble sort and using the compareto method ignoring case. also checks whether the list is empty before commencing
    private void sortList(){
        if(Entries!=0) {
            for (int i = 0; i < Entries - 1; i++) {
                int min = i;
                for (int j = i + 1; j < Entries; j++) {
                    if (Book.get(j).getName().compareToIgnoreCase(Book.get(min).getName()) < 0) {
                        min = j;
                    }
                }
                Contact temp = Book.get(i);
                Book.set(i, Book.get(min));
                Book.set(min, temp);
            }
        }
    }



}
