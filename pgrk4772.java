import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
public class pgrk4772{
public static DecimalFormat printformat=new DecimalFormat("0.0000000");
public static void main(String[] args)
	{
	//Validating Input
	int problemvalidity=validate_inputsize(args);
	if(problemvalidity==0)
	System.exit(0);
	else
	{
	int no_iterations=0;
	int no_maxvertex=10;
	int no_vertex=0;
	int no_edges=0;
	int[][] mainvector=null;
	try
	{
	Scanner inputfile=new Scanner(new File(args[2]));
	no_vertex=inputfile.nextInt();
	no_edges=inputfile.nextInt();
	//Creating vector matrix
	mainvector=new int[no_vertex][no_vertex];
	for(int i=0;i<no_vertex;i++)
	for(int j=0;j<no_vertex;j++)
	{
	mainvector[i][j]=0;
	}
	for(int k=0;k<no_edges;++k)
	mainvector[inputfile.nextInt()][inputfile.nextInt()]=1;
	inputfile.close();
	}
	catch(Exception e)
	{
	System.out.println(e+":File Invalid or does not exist");
	System.exit(0);
	}
	double init_vertex_val=0.0;
	double problem_errRate=0.0;
	double[] basevalues=new double[3];
	basevalues=assignments(args,no_vertex,no_maxvertex);
	int[] out_edge=new int[no_vertex];
	out_edge=calcOutEdge(mainvector,no_vertex,out_edge);
	double[] rank=new double[no_vertex];
	double[] prev_rank=new double[no_vertex];
	for(int i=0;i<no_vertex;i++)
	prev_rank[i]=basevalues[1];
	if(no_vertex<=no_maxvertex)
	{
	System.out.print(String.format("%s %3s","Base :","0")+" :");
	for(int i=0;i<no_vertex;i++)
	System.out.print(" P["+i+"]="+printformat.format(prev_rank[i]));
	}
	if(basevalues[0]>0)
	{
	for(int it=1;it<=basevalues[0];it++)
	{
	for(int i=0;i<no_vertex;i++)
	rank[i]=0.0;
	rank=calcRank(mainvector,rank,prev_rank,out_edge,no_vertex);
	System.out.println();
	System.out.print(String.format("%s %3s", "Iter :", it)+" :");
	rank=Formula_google(rank,no_vertex,no_maxvertex);
	for(int i=0;i<no_vertex;i++)
	prev_rank[i]=rank[i];
	}
	}
	else
	{
	int iter=0;
	do
	{
	if(iter!=0)
	{
	for(int i=0;i<no_vertex;i++)
	prev_rank[i]=rank[i];
	}
	for(int i=0;i<no_vertex;i++)
	rank[i]=0.0;
	rank=calcRank(mainvector,rank,prev_rank,out_edge,no_vertex);
	iter++;
	if(no_vertex<=no_maxvertex)
	{
	System.out.println();
	System.out.print(String.format("%s %3s", "Iter :", iter)+" :");
	}
	rank=Formula_google(rank,no_vertex,no_maxvertex);
	}while(compareErrorRate(rank,prev_rank,basevalues[2],no_vertex)==false||compareErrorRate(rank,prev_rank,basevalues[2],no_vertex)==false);

	if(no_vertex>no_maxvertex)
	{
	System.out.println(String.format("%s %3s", "Iter :", iter)+" :");
	for(int i=0;i<no_vertex;i++)
	System.out.println(" P["+i+"]="+printformat.format(rank[i]));
	}
	}
	System.out.println();
	}
	
}

public static double[] calcRank(int[][] m,double[] r,double[] pr,int edge[],int n)
{
	for(int i=0;i<n;i++)
	for(int j=0;j<n;j++)
	if(m[j][i]==1)
	r[i]+=pr[j]/edge[j];
	return r;
}
public static double[] Formula_google(double[] r,int n,int max)
{
	double d=0.85;
	for(int i=0;i<n;i++)
	{
	r[i]=(1-d)/n+(d*r[i]);
	if(n<=max)
	System.out.print(" P["+i+"]="+printformat.format(r[i]));
	}	
	return r;
}
//Validating inputsize	
public static int validate_inputsize(String[] inputs)
{
	if(inputs.length<3)
	{
	System.out.println("Too less arguments.Please enter arguments as below");
	System.out.println("Argument format:pgrank4772 iterations(int) initialvalue(int) filename(name of the file)");
	return 0;
	}
	else if(inputs.length>3)
	{
	System.out.println("Too Many arguments.Please enter arguments as below");
	System.out.println("Argument format: hits8719 iterations(int) initialvalue(int) filename(name of the file)");
	return 0;
	}
	else
	{
	return 1;	
	}

}
//Assigning base values to iterations,initialvalue
public static double[] assignments(String [] args,int vcount,int max_vcount)
{
double[] result=new double[3];
result[0]=Double.parseDouble(args[0]);
	if(vcount>max_vcount)	{
	result[0]=0;
	result[1]=getIniVal(-1,vcount);
	result[2]=getErrorRate(result[0]);
	}
	else if(vcount>0 && vcount<=max_vcount)
	{
	if(result[0]<=0)
	result[2]=getErrorRate(result[0]);
	result[1]=getIniVal(Double.parseDouble(args[1]),vcount);
	}
	else
	{
	System.out.println("Vertex count is Negative.Please check input file.");
	System.exit(1);
	}
return result;
}
//getIniVal assigns  the initial values
public static double getIniVal(double inputval,int inputcount)
{
double returnval=0.0;
if(inputval==0)
returnval=0.0;
else if(inputval==1)
returnval=1.0;
else if(inputval==-1)
returnval=1.0/inputcount;
else if(inputval==-2)
returnval=1.0/Math.sqrt(inputcount);
return returnval;
}
//getErrorRate assigns error rrate for the problem
public static double getErrorRate(double ival)
{
double val_iterations=0.0;
if(ival==0)
val_iterations= 1.0/Math.pow(10.0,5.0);
else
val_iterations=1.0/Math.pow(10.0,-ival);
return val_iterations;
}

public static int[] calcOutEdge(int[][] m,int n,int[] out)
{
for(int i=0;i<n;i++)
{out[i]=0;
for(int j=0;j<n;j++)
{
out[i]+=m[i][j];
}
}
return out;
}

public static double[] Normalize4772(double[] v,int n)
{
double normby=0.0;
double total=0.0;
for(int i=0;i<n;i++)
total=total+(v[i]*v[i]);
normby=Math.sqrt(total);
for(int i=0;i<n;i++)
v[i]=v[i]/normby;
return v;
}

public static boolean compareErrorRate(double[] c,double[] p,double rate,int no)
{
for(int i=0;i<no;i++)
{
if(Math.abs(p[i]-c[i])>rate)
return false;
}
return true;
}
}
