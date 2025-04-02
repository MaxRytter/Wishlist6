package org.example.wishlist6.Service;


        import org.example.wishlist6.Module.User;
        import org.example.wishlist6.Repository.WishlistRepository;

        import java.util.Scanner;
 /** jbcrypt er en package der kan kryptere passwords, men kan ikke importere den som dependency i maven?? tjek op på dette senere...
  import org.mindrot.jbcrypt.BCrypt; **/


public class UserRegistration {
     private final WishlistRepository wishlistRepository;


     public UserRegistration(WishlistRepository wishlistRepository) {
         this.wishlistRepository = wishlistRepository;  // Initialize repository
     }


public void register() {



    Scanner scanner = new Scanner(System.in);


    System.out.println("Indtast brugernavn");
    String userName = scanner.nextLine();

    System.out.println("Indtast email");
    String userEmail = scanner.nextLine();

    System.out.println("Indtast password");
    String passwordHash = scanner.nextLine();

    // String passwordHash = passwordHash(password);


    if (userName.isEmpty() || userEmail.isEmpty() || passwordHash.isEmpty()) {
        System.out.println("Alle felter skal udfyldes.");
        return;
    }


    User user = new User(userName, userEmail, passwordHash);
    wishlistRepository.save(user);


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

// kommentar som test for push
}
