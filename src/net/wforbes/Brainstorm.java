package net.wforbes;

import java.util.Date;
import java.util.UUID;

public class Brainstorm {

    //vitals
    public class Vital{
        public UUID uuid;
        //health
        //endurance
        //mana
        //focus
    }

    //statistics
    public class Statistic{
        //strength
        //stamina
        //agility
        //dexerity
        //intellect
        //wisdom
        //charisma
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
        //druid
        //cleric

    }

    public class Player{
        public UUID uuid;
        public User user;
        public String name;
    }

    public class User{
        public UUID uuid;
        public String userName;
        public String passhash;
        public String emailAddress;
        public Account account;
    }

    public class Account{
        public UUID uuid;
        public int accountNumber;
        public String accountName;
        public Person person;
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
}
