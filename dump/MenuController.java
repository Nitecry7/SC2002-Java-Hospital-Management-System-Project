
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class MenuController implements  IMenuController {

    User user;
    protected  Method[] methods;
  // vvv Later, if we want, we can initialize this to a string list declared in the User (sub)class to display the menu items in a specific order
    protected String[] methodNames = {};

    public MenuController(User user) throws NoSuchMethodException {
        this.user = user;
        initializeMethods(this.methodNames);
    }


     private void initializeMethods(String[] methodNames) throws NoSuchMethodException{

        List<Method> tempMethods = new ArrayList<>();
        List<String> declaredMethods = new ArrayList<>(Arrays.asList(methodNames));
        
        Class<?> userClass = user.getClass();

        while (User.class.isAssignableFrom(userClass)) {
            tempMethods.addAll(Arrays.asList(userClass.getDeclaredMethods()));
            userClass = userClass.getSuperclass();
        }


        List<Method> tempMethods2 = new ArrayList<>(tempMethods);
        
        for (Method method : tempMethods2) {
  
           int index;
 
            if ((index = declaredMethods.indexOf(method.getName())) != -1) {
               
                tempMethods.add(index, tempMethods.remove(tempMethods.indexOf(method)));
            }
            else if (method.getName().startsWith("_") && !declaredMethods.contains(method.getName())) {
                declaredMethods.add(method.getName());
            }
            else {
                tempMethods.remove(method);
            }

        }
        
        if (tempMethods.size() != declaredMethods.size()) {
            throw new NoSuchMethodException("Too many/few matching methods");
        }
        else {
            methods = tempMethods.toArray(Method[]::new);
            this.methodNames = declaredMethods.toArray(String[]::new);
        }

        for (String s : this.methodNames) {
            System.out.println(s);
        }

    }

    private void displayMenu() {

        System.out.println("\n\n-------------MENU-------------\n\n");

        //Displays menu method from 1 to the total number of methods starting with '_'
        int x = 1;
        for (String name:methodNames) {
            //Splits method name by '_' and displays it along with the menu choice number (x) E.g: _View_Schedule() becomes "1. View Schedule"
            System.out.println(x + "." + name.replace("_", " ")); 
            x++;
            
        }
        System.out.println("\n\nInput a valid number choice");

    }

    @Override
    public final void takeInput() {

        Scanner in = new Scanner(System.in);

        try {
            displayMenu();
            //invoke the chosen method
            int choice = in.nextInt();
            in.nextLine();
            methods[choice-1].invoke(user);
        } 

        catch (Exception e) {
            System.out.println("That isn't a valid choice, or something went wrong.");
           
        }
    }


}
