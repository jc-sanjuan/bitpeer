
import lpsolve.*;
//import java.util.*;

public class Demo {

  public static void main(String[] args) {
    int j=4; // downloaders
    int k=3; //uploaders
    int i=2; //pieces

    int ji = 8; //downloaders*no. of pieces
    
    /*
    int[][] dUtility1 = {{2,3,0},{0,4,0},{0,0,6},{5,0,3}};
    int[][] dUtility2 = {{1,4,0},{0,3,0},{6,0,7},{1,0,1}};
    int[][] uUtility1 = {{7,2,0},{0,1,0},{0,0,3},{1,0,1}};
    int[][] uUtility2 = {{4,3,1},{0,1,0},{5,0,6},{2,0,2}};
    */
    double[][][][] all ={{{{2,3,0},{0,4,0},{0,0,6},{5,0,3}}, {{1,4,0},{0,3,0},{6,0,7},{1,0,1}}},{{{7,2,0},{0,1,0},{0,0,3},{1,0,1}},{{4,3,1},{0,1,0},{5,0,6},{2,0,2}}}};
    double[] constraint = new double[j*i*k*2];
    double[] objfnmax = new double[j*i*k];
    double[] objfn = new double [i*j*k*2];

    double[] Tk = {4,3,3};
    int index=0;
    for(int l=0; l<i; l++){ //for every piece
      for(int o=2; o<2; o++){ //downloader and uploader utility
        for(int m=0; m<j; j++){ //for every downloader
          for(int n=0; n<k; n++){ //for every uploader
            objfn[index] = all[l][o][m][k];
            constraint[index] = 1;
          }
        }
      }
    }

    double constraint1 = 1;
    String temp1="";
    String temp2="";
    try {
      // Create a problem with 4 variables and 0 constraints
      LpSolve solver = LpSolve.makeLp(0, 48);
      solver.setMaxim();
      // add constraints

      //every row(downloader) must be equal to 1
      int r=0;
      for(int q=0; q<(i*j*k*2); q=q+3){
        r=i*j*k*2;
        if(q>0){
          temp1=temp1+"0 0 0 ";
        }
        while(r>(q+3)){
          temp2=temp2+" 0";
          r--;
        }
        solver.strAddConstraint(temp1+"1 1 1"+temp2, LpSolve.EQ, constraint1);
        temp2="";
      }

      //The total uploader utility must be lesser than or equal to uploader capacity
      
      String temp3="";
      String temp4="";
      temp1="";
      temp2="";

      for(int v=0; v<i*j*k; v++){
        temp4=temp4+"0 ";
      }
      
      for(int s=0; s<k; s++){

        if(s>0){
          for(int t=s; t%k>0; t--){
            temp1=temp1+"0 ";
          }
        }

        if(s<k-1){
          for(int u=s; u%k<k-1; u++){
            temp2=temp2+" 0";
          }
        }

        for(int w=0; w<j*i; w++){ 

          temp3 = temp3 + temp1 +"1"+ temp2+" ";

        }
        temp3 = temp4+temp3;
        temp1="";
        temp2="";
        
        solver.strAddConstraint(temp3,LpSolve.LE, Tk[s]);

        temp3="";
     }

     //
     solver.addConstraint(constraint, LpSolve.LE, 1);
     solver.addConstraint(constraint, LpSolve.GE, 0);
     


     // solver.AddConstraint("3 2 2 1", LpSolve.EQ, 4);
      //solver.strAddConstraint("0 4 3 1", LpSolve.GE, 3);

      // set objective function
      solver.setObjFn(objfn);

      // solve the problem
      solver.solve();

      // print solution
      System.out.println("Value of objective function: " + solver.getObjective());
      double[] var = solver.getPtrVariables();
      for (int p = 0; p < var.length; p++) {
        System.out.println("Value of var[" + p + "] = " + var[p]);
      }

      // delete the problem and free memory
      solver.deleteLp();
    }
    catch (LpSolveException e) {
       e.printStackTrace();
    }
  }

}
