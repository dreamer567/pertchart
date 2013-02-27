/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pertchart;

import java.awt.*;
import javax.swing.*;

import no.geosoft.cc.geometry.Geometry;
import no.geosoft.cc.graphics.*;
/**
 *
 * @author Aidan
 */
public class Start extends JFrame{

    private GScene scene;
    private ListOfTasks taskList;
    
    public Start() {
        
        super("Pert Chart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //will need to change this **********************
        taskList = new ListOfTasks();
        
        //Create the gui window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);
        
        JPanel graphicsPanel = new JPanel();
        
        //Create the graphics canvas
        GWindow window = new GWindow();
        mainPanel.add(window.getCanvas(), BorderLayout.CENTER);
        
        //Create scene
        scene = new GScene(window, "scene");
        
        double w0[] = {0.0, 1500.0, 0.0};
        double w1[] = {1500.0, 1500.0, 0.0};
        double w2[] = {0.0, 0.0, 0.0};
        scene.setWorldExtent(w0, w1, w2);
        scene.shouldZoomOnResize(false);
        
        
        GStyle style = new GStyle();
        style.setForegroundColor(Color.BLUE);
        style.setBackgroundColor(new Color (255, 255, 255));
        style.setFont(new Font("Dialog", Font.BOLD, 10));
        scene.setStyle(style);
        
        
        //Gets the input from the user then creates the Task objects and also
        //adds them to the taskList.
        TextInput input = new TextInput();
        while(input.getFinished() == false) {
            input.inputTask();
            Task parentTask = null;
            int parentCounter = 0;
            double xPosition = 200;
            for(Task task : taskList.getTaskList()) {
                if(task.getTaskNumber() == input.getParentNum()) {
                    parentTask = task;
                    xPosition = xPosition + parentTask.getXPosition() + 200;
                }
                if(task.getParent() == parentTask) {
                    parentCounter++;
                }
            }
            double yPosition = 500 + parentCounter * 300;
            //xPosition = xPosition - parentCounter * 200;
            taskList.addTask(new Task(input.getName(), input.getTaskNum(), input.getNumOfDays(), "startDate", "endDate", scene, parentTask, xPosition, yPosition));
        }
        /*
        while(input.getFinished() == false) {
            input.inputTask();
            Task parentTask = null;
            double xPosition = 500;
            for(Task task : taskList.getTaskList()) {
                if(task.getTaskNumber() == input.getParentNum()) {
                    parentTask = task;
                    xPosition = xPosition + parentTask.getX();
                }
            }
            taskList.addTask(new Task (input.getName(), input.getTaskNum(), input.getNumOfDays(), "testStartDate",
                      "testEndDate", scene, parentTask, xPosition, 500.0));
        }
        */

        //Checks if more than 3 tasks have been created, if they have it changes
        //the size of the scene world extent.
        if(taskList.lengthOfTaskList() > 3) {
            double newSize = 1500 + taskList.lengthOfTaskList() * 500;
            double we0[] = {0.0, newSize, 0.0};
            double we1[] = {newSize, newSize, 0.0};
            double we2[] = {0.0, 0.0, 0.0};
            scene.setWorldExtent(we0, we1, we2);
        }
        
        pack();
        setSize (new Dimension(500, 500));
        
        /*
         * Checks if more than 3 tasks have been created, if they have it changes
         * the size of the scene world extent.
         */
        
        setVisible(true);
        
    }
}
