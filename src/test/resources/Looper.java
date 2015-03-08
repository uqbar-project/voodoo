public class Looper {
  
  private int count;
  
  public void loop(int times){
    int i = 0;
    
    while(i < times){
      this.count++;
      i++;
    }
  }
  
  public int count(){
    return this.count;
  }
}