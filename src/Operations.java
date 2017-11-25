public enum Operations {

	ADD("+"){
		public Double operation(Double a, Double b){
			return a + b; 
		}
	},
	SUB("-") {
		public Double operation(Double a, Double b){
			return a - b; 
		}
	},
	MUL("*") {
		public Double operation(Double a, Double b){
			return a * b; 
		}
	},
	DIV("/") {
		public Double operation(Double a, Double b){
			if(b == 0){
				return null;
			}
			return a / b;
		}
	};
	
	private String operation;
	
	public abstract Double operation(Double a, Double b);
	
	Operations (String operation ){
		this.operation = operation;
	}
	
	public String getOperation(){
		return operation;
	}
	
}