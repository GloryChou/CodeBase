package per.zyf.entity;

public class Person {  
    
    private int pid;  
    private String pname;  
    private int page;  
      
    private int pageIndex;  
    private int pageSize;  
    public int getPageIndex() {  
        return pageIndex;  
    }  
    public void setPageIndex(int pageIndex) {  
        this.pageIndex = pageIndex;  
    }  
    public int getPageSize() {  
        return pageSize;  
    }  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
    public Person() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
    public Person(int pid, String pname, int page) {  
        super();  
        this.pid = pid;  
        this.pname = pname;  
        this.page = page;  
    }  
    public int getPid() {  
        return pid;  
    }  
    public void setPid(int pid) {  
        this.pid = pid;  
    }  
    public String getPname() {  
        return pname;  
    }  
    public void setPname(String pname) {  
        this.pname = pname;  
    }  
    public int getPage() {  
        return page;  
    }  
    public void setPage(int page) {  
        this.page = page;  
    }  
    @Override  
    public String toString() {  
        return "Person [pid=" + pid + ", pname=" + pname + ", page=" + page + "]";  
    }  
    public Person(String pname, int page) {  
        super();  
        this.pname = pname;  
        this.page = page;  
    }  
      
      
}