package io.coronalive.models;

public class LocationStats {

    private String state;
    private String country;
    private int latestTotalConfirmedCases;
    private int diffFromPrevDayConfirmedCases;
    private int latestTotalDeathCases;
    private int diffFromPrevDeathCases;
    private int latestTotalRecoveredCases;
    private int diffFromPrevDayRecoveredCases;

    

    public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public int getLatestTotalConfirmedCases() {
		return latestTotalConfirmedCases;
	}



	public void setLatestTotalConfirmedCases(int latestTotalConfirmedCases) {
		this.latestTotalConfirmedCases = latestTotalConfirmedCases;
	}



	public int getDiffFromPrevDayConfirmedCases() {
		return diffFromPrevDayConfirmedCases;
	}



	public void setDiffFromPrevDayConfirmedCases(int diffFromPrevDayConfirmedCases) {
		this.diffFromPrevDayConfirmedCases = diffFromPrevDayConfirmedCases;
	}



	public int getLatestTotalDeathCases() {
		return latestTotalDeathCases;
	}



	public void setLatestTotalDeathCases(int latestTotalDeathCases) {
		this.latestTotalDeathCases = latestTotalDeathCases;
	}



	public int getDiffFromPrevDeathCases() {
		return diffFromPrevDeathCases;
	}



	public void setDiffFromPrevDeathCases(int diffFromPrevDeathCases) {
		this.diffFromPrevDeathCases = diffFromPrevDeathCases;
	}



	public int getLatestTotalRecoveredCases() {
		return latestTotalRecoveredCases;
	}



	public void setLatestTotalRecoveredCases(int latestTotalRecoveredCases) {
		this.latestTotalRecoveredCases = latestTotalRecoveredCases;
	}



	public int getDiffFromPrevDayRecoveredCases() {
		return diffFromPrevDayRecoveredCases;
	}



	public void setDiffFromPrevDayRecoveredCases(int diffFromPrevDayRecoveredCases) {
		this.diffFromPrevDayRecoveredCases = diffFromPrevDayRecoveredCases;
	}



	@Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalConfirmedCases +
                '}';
    }
}
