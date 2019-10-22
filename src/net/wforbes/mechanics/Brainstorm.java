package net.wforbes.mechanics;

import java.util.Date;
import java.util.UUID;

public class Brainstorm {



    //vitals
    //statistics
    public class Statistic{

    }

    //skills
    public class Skill{
        public String name;
        public int level;
        public double condition;
    }

    public class Class{
        //warrior
        //rogue
        //paladin
        //shadowknight
        //wizard
        //mage
        //bard
    }

    public class Person{
        public UUID uuid;
        public String firstName;
        public String lastName;
        public Address address;
        public String phoneNumber;
        public String email;
        public Date birthday;
    }

    public class Address{
        public UUID uuid;
        public int number;
        public String name;
        public String companyName;
        public String street;
        public int poBox;
        public String unit;
        public String district;
        public String county;
        public String city;
        public String state;
        public int postalCode;
    }

    public class User{
        public UUID uuid;
        public String userName;
        public String emailAddress;
        public Person person;
        public Account account;
    }

    public class Account{
        public UUID uuid;
        public int accountNumber;
        public String accountName;
    }

    public class Player{
        public UUID uuid;
        public User user;
        public String name;
    }

}
