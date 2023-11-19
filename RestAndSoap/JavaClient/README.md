# Heavy Java Client

## Java swing map UI

### JFrame :

- JFrame is a class in Java Swing for creating graphical user interfaces? it's essentially a window where the your application
  ```java
    JFrame frame = new JFrame("Title of the Window");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300); // width, height in pixels
    frame.setVisible(true); // Show the frame
  ```
  
### JPanel

- JPanel is a container that can hold other GUI components. It's like a sub-section within a JFrame.
  ```java
  JPanel panel = new JPanel();
  panel.add(new JButton("Click me")); // Adding a button to the panel
  frame.add(panel); // Adding the panel to the frame
  ```