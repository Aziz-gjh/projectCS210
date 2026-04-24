public class NEOM_Core {
    AVLTree sectors; 
    TaskQueue deployment;
    ActionStack undoLog;
    ArchiveList history;

    public NEOM_Core() {
        sectors = new AVLTree();
        deployment = new TaskQueue();
        undoLog = new ActionStack();
        history = new ArchiveList();
    }
    
    public void addTask(int sectorID, String taskID, String desc) {
        TaskNode newTask = new TaskNode(sectorID, taskID, desc);
        //insert task into Node in AVL tree if it exists, else make the node and insert
        if(sectors.Search(sectorID) == null) {
            sectors.Insert(sectorID);
        }
        sectors.Search(sectorID).tasks.Add(newTask);
        //insert into Queue
        deployment.Enqueue(newTask);
        //push onto the stack
        undoLog.Push(newTask);
        
    }

    public void processNextTask() {
        //dequeue next task
        TaskNode nextTask = deployment.Dequeue(); 
        //update its status in the AVL Tree to "Completed"
        sectors.completeTask(nextTask);
        //Insert it at the tail of the ArchiveList
        history.Append(nextTask);
    }

    public void undoLastAction() {
        //Pop the stack
        TaskNode removedTask = undoLog.Pop();
        //Remove the popped task from AVL tree and Queue \
        sectors.Remove(removedTask)
        deployment.Dequeue(removedTask) //WE MUST ASK THE DR. ABOUT THIS

        //extra: Update log for undos as well
    }

    public void systemAudit() {
        //Perform LVR traversal of AVL tree and print all sectors and their tasks
        sectors.Traverse() //I'll implement the printing inside the Traverse
    }

    public void printDeploymentHistory() {
        //Traverse ARchiveList from head to tail and print all tasks in order
        history.Traverse() //Abdulaziz will implement the printing in the Traverse
    }

    public void searchSector(int sectorID) {
        //Search the AVL tree for sectorID
        TaskNode SearchedTask = sectors.Search(sectorID)
        //print number of comparisons vs. total Sectors (demonstrate that it is O(log n))
        //WIP HERE

    }
    @Override
    public String toString() {
        return "";
    } 
    
}
}
