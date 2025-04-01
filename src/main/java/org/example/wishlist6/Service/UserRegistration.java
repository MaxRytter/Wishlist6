package org.example.wishlist6.Service;


        import org.example.wishlist6.Module.User;

        import java.util.Scanner;
 /** jbcrypt er en package der kan kryptere passwords, men kan ikke importere den som dependency i maven?? tjek op på dette senere...
  import org.mindrot.jbcrypt.BCrypt; **/


public class UserRegistration {
private UserService userService;
public UserRegistration(UserService userService) {
    this.userService = userService;
}
public void register() {



    Scanner scanner = new Scanner(System.in);

    System.out.println("Indtast brugerid");
    int userId = scanner.nextInt();
    // ovenstående er bare en midlertidig løsning, så programmet ikke klager over at userId ikke er defineret  - userId skal ikke vælges af brugeren,
    // men skal tildeles automatisk - det gøres somehow når det gemmes i sql databasen?




    //her skal være inputboks
    System.out.println("Indtast brugernavn");
    String userName = scanner.nextLine();

    //her skal være inputboks
    System.out.println("Indtast email");
    String userEmail = scanner.nextLine();

    //her skal være inputboks
    System.out.println("Indtast password");
    String passwordHash = scanner.nextLine();

    // String passwordHash = passwordHash(password);


    if (userName.isEmpty() || userEmail.isEmpty() || passwordHash.isEmpty()) {
        System.out.println("Alle felter skal udfyldes.");
        return;
    }


/**
 Her ville jeg tjekke at der ikke kan oprettes en ny bruger med samme email som en anden bruger,
 men listen users fra UserService klassen er private og contained i klassen. find ud af en løsning til dette!
    for (User user : users) {
        if (user.getUserEmail().equals(userEmail)) {
            System.out.println("En bruger er allerede registreret med denne email");
            return;
        }

**/
    userService.registerUser(userId, userName, userEmail, passwordHash);

    scanner.close();
}
/** igen, kryptering
    private String passwordHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
**/



/**

 nedenstående skal sættes ind i pom.xml under dependencies, men lige nu virker det ikke...
 <dependency>
 <groupId>org.mindrot</groupId>
 <artifactId>jbcrypt</artifactId>
 <version>0.4</version>
 </dependency>


 **/
}
