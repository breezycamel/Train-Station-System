package trainstation.model;

public class TrainRoute {
	private static final long serialVersionUID = 1L;
    private String trainId;
    
    private int totalFare;
    private String arrivalTime;
    private String departTime;
    private String origin, destination;
    private String lineName;
  
    public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public TrainRoute(String trainId, String arrivalTime, String departTime, String origin, String destination, int totalFare) {
    	this.trainId = trainId;
    	this.arrivalTime = arrivalTime;
    	this.departTime = departTime;
    	this.origin = origin;
    	this.destination = destination;
    	this.totalFare = totalFare;
    }
    public String gettrainId() {
        return trainId;
    }
    public void settrainId(String trainId) {
        this.trainId = trainId;
    }
    
    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public String getDepartTime() {
    	return departTime;
    }
    public void setDepartTime(String departTime) {
    	this.departTime = departTime;
    }
    
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public int getTotalFare() {
    	return totalFare;
    }
    public void setTotalFare(int totalFare) {
    	this.totalFare = totalFare;
    }
}
