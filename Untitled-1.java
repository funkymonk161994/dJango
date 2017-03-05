import java.util.Arrays;
import java.util.Random;

public class Main {
  public static void main(String[] args){
    Random r = new Random(System.currentTimeMillis());
    int n = r.nextInt(101) + 50;
    int[] a = new int[n];
    for(int i = 0; i < n; i++)
      a[i] = r.nextInt(100);
    n = r.nextInt(101) + 50;
    int[] b = new int[n];
    for(int i = 0; i < n; i++)
      b[i] = r.nextInt(100);
    SortThread t1 = new SortThread(a);  //It is not a thread yet
    SortThread t2 = new SortThread(b);  //It is not a thread yet
    MergeThread m = new MergeThread(t1.get(),t2.get());//It is not a thread yet
    Thread srt_thrd1= new Thread(t1);
    Thread srt_thrd2= new Thread(t2);
    Thread mrg_thrd= new Thread(m);
    srt_thrd1.start();
    srt_thrd2.start();
    try {
      srt_thrd1.join();
      srt_thrd2.join();
      mrg_thrd.start();
      System.out.println(Arrays.toString(m.get()));
      
    } catch (Exception e) {
      //TODO: handle exception
      System.out.println(e.toString());
    }
    
  }
}

public class SortThread implements Runnable{
  int[] x; 
  
  public SortThread(int[] x){
    this.x = x;
    
  }
    public void run(){
      sort(x);
  }
    
  private void sort(int[] x){
    for(int i = 0; i < x.length ; i++){
      int indexOfSmallest = findIndexOfSmallest(x, i);
      int t = x[i];
      x[i] = x[indexOfSmallest];
      x[indexOfSmallest] = t;
    }
  }
      
  private int findIndexOfSmallest(int[] a, int from){
    int indexOfSmallest = from;

    for(int i = from; i < a.length; i++)
      if(a[i] < a[indexOfSmallest])
        indexOfSmallest = i;
    return indexOfSmallest;
  }
  
  
  public int[] get(){
    return x;
  }
}

public class MergeThread implements Runnable{
  int[] a;
  int[] b;
  int[] c;

  public MergeThread(int[] a, int[] b){
    this.a = a;
    this.b = b;
    c = new int[a.length + b.length];
    
  }
  public void run(){
    merge();
  }

  private void merge(){
    int aIndex = 0, bIndex = 0, cIndex = 0;
    while(aIndex < a.length && bIndex < b.length)
      if(a[aIndex] < b[bIndex])
        c[cIndex++] = a[aIndex++];
      else
        c[cIndex++] = b[bIndex++];

    while(aIndex < a.length)
      c[cIndex++] = a[aIndex++];
    
    while(bIndex < b.length)
      c[cIndex++] = b[bIndex++];
  }
  
  public int[] get(){
    return c;
  }
}
