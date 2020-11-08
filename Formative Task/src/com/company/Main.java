package com.company;
// Formative Task, Mazen Omar, 29042121, last edited at 07:10 BST 1/11/2020
//The following is a basic number storing system as outlined in the brief description, additionally  I have added a few features taught to us in the ADS2 module along with a file io writing & reading system
// similar to the one we were taught in C++ last year. The project contains 3 classes being Main, TelephoneBook ( the arraylist of contacts and all its methods) and the Contact which is the object stored
//in the TelephoneBook class.
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// Setting up the menu system and taking in the file to be used for the program
        Scanner sc=new Scanner(System.in);
        System.out.println("=============================");
        System.out.println("Telephone Book System");
        System.out.println("=============================");
        System.out.println("Please enter the name of the telephone book text file you would like to use followed by '.txt'.\nIf you don't have one, enter 'TelephoneBook.txt'.");
       String fileName =sc.nextLine();
        TelephoneBook telephoneBook = new TelephoneBook(fileName);
        int options;
        //Outlining menu and various options, some use basic calls to the functions in TelephoneBook, others require casing and checks along with printing around them to explain everything and confirm
        //decisions to the user. The whole code tree and selection of various options has been tested extensively using a variety of cases. However there is a lack of testing on whether the user
        //inputs strings instead of ints or vice versa, I thought this would be too complex
        do{
            System.out.println("1. Add a Contact \n2. Edit a Contact \n3. Search for a Contact \n4. Delete a Contact \n5. Print all contacts \n6. Print a contact \n7. Close the system");
            options= telephoneBook.getInput(7,false);
            switch(options) {
                case 1: //Add a Contact method, which calls upon the function in TelephoneBook
                        telephoneBook.addContact();
                    break;

                case 2: //Edit a Contact, checks if user knows index of the contact to be edited and if not, searches for them and then asks to edit their contact
                    if(telephoneBook.getEntries()!=0) {
                        System.out.println("Do you know the index of the contact you want to edit? \n1.Yes \n2.No \n");
                        if (telephoneBook.getInput(2, false) == 1) {
                            System.out.println("What is the index? \n");
                            telephoneBook.editContact(telephoneBook.getInput(telephoneBook.getEntries(), true));
                        } else {
                            System.out.println("Ok, let's search for them :), please enter the name you would like to search for(Not Case Sensitive):");
                            String name = sc.nextLine();
                            int index = telephoneBook.searchContact(name);
                            if (index != -1) {
                                System.out.println("The index for " + name + " is " + index + ". \n");
                                System.out.println("Would you still like to edit this contact? \n1.Yes \n2.No \n");
                                if (telephoneBook.getInput(2, false) == 1) {
                                    telephoneBook.editContact(index);
                                }
                            } else {
                                System.out.println("This contact is not found. \n");
                            }
                        }
                    }
                    else{
                        System.out.println("Telephone Book is empty");
                    }

                    break;

                case 3: // Search for a Contact, wraps printing and integrates functions which can be done to contacts after they have been found
                    if(telephoneBook.getEntries()!=0) {
                        System.out.println("What is the name you would like to search for(Not Case Sensitive)? ");
                        String name = sc.nextLine();
                        int index = telephoneBook.searchContact(name);
                        if (index != -1) {
                            System.out.println("The index for " + name + " is " + index + ". \nWhat would you like to do with this information? \n1.Edit this contact \n2.Delete this contact \n3.Print their details \n");
                            int input = telephoneBook.getInput(3, false);
                            if (input == 1) {
                                telephoneBook.editContact(index);
                            } else if (input == 2) {
                                telephoneBook.deleteContact(index);
                            } else {
                                System.out.println("Alright here you go: \n Name: " + telephoneBook.getDetails(index, true) + " \n Telephone Number: " + telephoneBook.getDetails(index, false) + " \n");
                            }
                        } else {
                            System.out.println("The contact is not found.\n");
                        }
                    }
                    else{
                        System.out.println("Telephone Book is empty");
                    }
                    break;

                case 4:  //Delete a Contact, wraps printing around deleting a contact and searching for them if the user does not know them
                    if(telephoneBook.getEntries()!=0) {
                        System.out.println("Do you already know the index number for this contact?\n1.Yes \n2.No \n");
                        if (telephoneBook.getInput(2, false) == 1) {
                            System.out.println("What is the index of the contact? \n");
                            int position = telephoneBook.getInput(telephoneBook.getEntries(), true);
                            telephoneBook.deleteContact(position);
                        } else {
                            System.out.println("Would you like to search for this contact to delete them?\n1.Yes \n2.No");
                            if (telephoneBook.getInput(2, false) == 1) {
                                System.out.println("Please enter the name of the person you would like to search:");
                                String searchValue = sc.nextLine();
                                int lookFor = telephoneBook.searchContact(searchValue);
                                System.out.println("Ok, so now you have the index value for the contact you searched for, would you like to delete them? \n1.Yes \n2.No\n");
                                if (telephoneBook.getInput(2, false) == 1) {
                                    telephoneBook.deleteContact(lookFor);
                                    System.out.println("Contact successfully removed from the matrix :)");
                                } else {
                                    System.out.println("Ok, later perhaps.");
                                }
                            } else {
                                System.out.println("Well you need to search for them so maybe later perhaps.");
                            }
                        }
                    }
                    else{
                            System.out.println("Telephone Book is empty");
                        }
                    break;

                case 5: // Print all Contacts, prints all contacts in the telephone book and their details, in a sorted alphabetical order
                    if(telephoneBook.getEntries()!=0) {
                        for (int i = 0; i < telephoneBook.getEntries(); i++) {
                            System.out.println("Name: " + telephoneBook.getDetails(i, true) + "\nTelephone Number: " + telephoneBook.getDetails(i, false) + " \n");
                        }
                    }
                    else{
                        System.out.println("Telephone Book is empty");
                    }
                    break;

                case 6: // Print a Contact, wraps printing a specific contact, searching for them,etc
                    if(telephoneBook.getEntries()!=0) {
                        System.out.println("Do you know the index of the person you wanna print details for? \n1.Yes \n2.No\n ");
                        if (telephoneBook.getInput(2, false) == 1) {
                            System.out.println("What is the index of the contact? \n");
                            int position = telephoneBook.getInput(telephoneBook.getEntries(), true);
                            System.out.println("Name: " + telephoneBook.getDetails(position, true) + "\nTelephone Number: " + telephoneBook.getDetails(position, false) + " \n");
                        } else {
                            System.out.println("Would you like to search for this contact to print their details?\n1.Yes \n2.No");
                            if (telephoneBook.getInput(2, false) == 1) {
                                System.out.println("Please enter the name of the person you would like to search:");
                                String searchValue = sc.nextLine();
                                int lookFor = telephoneBook.searchContact(searchValue);
                                if (lookFor != -1) {
                                    System.out.println("Ok, so now you have the index value for the contact you searched for, would you like to print their details them? \n1.Yes \n2.No");
                                    if (telephoneBook.getInput(2, false) == 1) {
                                        System.out.println("Name: " + telephoneBook.getDetails(lookFor, true) + "\nTelephone Number: " + telephoneBook.getDetails(lookFor, false) + " \n");
                                    } else {
                                        System.out.println("Ok, later perhaps.");
                                    }
                                } else {
                                    System.out.println("The contact is not found");
                                }
                            } else {
                                System.out.println("Well you need to search for them so maybe later perhaps.");
                            }
                        }
                    }else{
                    System.out.println("Telephone Book is empty");
                }
                        break;

                case 7: // Close the System, ends the program and saves the file by writing the array list to the txt file

                    telephoneBook.saveFile();
                    break;
            }
        }while(options!=7);

    }
}
