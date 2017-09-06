package itmo.visits_control.models;

public enum PersonInfoStatus {
	Ok(true, null), NotFoundInVisits(false, "Не найден в базе проходной!");
	private boolean isOk;
	private String message;

	private PersonInfoStatus(boolean isOk, String message) {
		this.isOk = isOk;
		this.message = message;
	}

	public boolean isOk() {
		return isOk;
	}

	
	public String getMessage() {
		return message;
	}

	

}
