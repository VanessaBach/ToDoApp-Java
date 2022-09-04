package TodoApp;

import controller.ProjectController;
import java.sql.Connection;
import model.Project;
import util.ConnectionFactory;

public class App {
    

    public static void main(String[] args) {
        
        ProjectController projectController = new ProjectController();
        
        Project project = new Project();
        project.setId(12);
        project.setName("Novo nome");
        project.setDescription("description");
        //projectController.save(project);
        
        projectController.update(project);
        
        
        
       
    }

   
}
