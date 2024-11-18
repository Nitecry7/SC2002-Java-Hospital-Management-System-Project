import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Controller to manage dynamic menus based on user roles.
 * The MenuController retrieves methods from the user class hierarchy and dynamically invokes them
 * based on user input, providing a tailored menu experience for different user roles.
 */
public class MenuController implements IMenuController {

    /**
     * The user interacting with the menu
     */
    private User user;
    /**
     * Methods dynamically initialized for the menu
     */
    protected Method[] methods;
    /**
     * Names of the methods for display and invocation
     */
    protected String[] methodNames = {};
    /**
     * Index of the logout option in the menu
     */
    private int logout = -1;

    /**
     * Constructs a MenuController for the specified user.
     *
     * @param user The user interacting with the menu.
     * @throws NoSuchMethodException If a required method is not found.
     */
    public MenuController(User user) throws NoSuchMethodException {
        this.user = user;
        initializeMethods(this.methodNames);
    }

    /**
     * Dynamically initializes methods for the menu based on the user role and the provided method names.
     *
     * @param methodNames An array of method names to include in the menu.
     * @throws NoSuchMethodException If a method in methodNames is missing or mismatched.
     */
    private void initializeMethods(String[] methodNames) throws NoSuchMethodException {
        List<Method> tempMethods = new ArrayList<>();
        List<String> declaredMethods = new ArrayList<>(Arrays.asList(methodNames));

        // Retrieve methods from the user's class hierarchy
        Class<?> userClass = user.getClass();
        while (User.class.isAssignableFrom(userClass)) {
            tempMethods.addAll(Arrays.asList(userClass.getDeclaredMethods()));
            userClass = userClass.getSuperclass();
        }

        List<Method> tempMethods2 = new ArrayList<>(tempMethods);

        for (Method method : tempMethods2) {
            int index;
            if ((index = declaredMethods.indexOf(method.getName())) != -1) {
                // Reorder methods based on their order in methodNames
                tempMethods.add(index, tempMethods.remove(tempMethods.indexOf(method)));
            } else if (method.getName().startsWith("_") && !declaredMethods.contains(method.getName())) {
                // Automatically add unnamed methods starting with '_'
                declaredMethods.add(method.getName());
            } else {
                tempMethods.remove(method);
            }
        }

        if (tempMethods.size() != declaredMethods.size()) {
            throw new NoSuchMethodException("Too many/few matching methods");
        } else {
            methods = tempMethods.toArray(Method[]::new);
            this.methodNames = declaredMethods.toArray(String[]::new);
            logout = declaredMethods.indexOf("_Logout") + 1;
        }
    }

    /**
     * Displays the menu options to the user.
     */
    private void displayMenu() {
        System.out.println("\n\n-------------MENU-------------\n\n");

        // Display menu options with indices
        int x = 1;
        for (String name : methodNames) {
            // Format method names by removing underscores and displaying in a readable format
            System.out.println(x + ". " + name.replace("_", " "));
            x++;
        }
        System.out.println("\n\nInput a valid number choice");
    }

    /**
     * Captures user input and invokes the corresponding menu methods.
     */
    @Override
    public final void takeInput() {
        Scanner in = new Scanner(System.in);

        int choice = -2;
        while (choice != logout) {
            try {
                displayMenu();
                // Capture user choice
                choice = in.nextInt();
                in.nextLine();
                if (choice == logout) {
                    System.out.println("Are you sure you want to logout? Enter Y to logout or any other key to continue");
                    if (!in.nextLine().toUpperCase().equals("Y")) {
                        choice = -2;
                        continue;
                    }
                }

                // Invoke the selected method dynamically
                methods[choice - 1].invoke(user);
            } catch (Exception e) {
                System.out.println("That isn't a valid choice, or something went wrong.");
                //e.printStackTrace();
            } 
        }
    }
}
