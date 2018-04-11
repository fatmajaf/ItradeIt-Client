package tn.esprit.sltsclient.Utils;

public class BSOption {

	   private double S;        // underlying asset price
	   private  double K;        // strike price option
	   private  double T;        // time to maturity option
	   private  double r;        // annual risk-free rate
	   private  double q;        // annual yield rate asset
	   private  double vol;      // underlying asset volatility
	   private  double price;    // price of option (if given)
	   private  char type;
	   
	   public double getS() {
		return S;
	}
	public void setS(double s) {
		S = s;
	}
	public double getK() {
		return K;
	}
	public void setK(double k) {
		K = k;
	}
	public double getT() {
		return T;
	}
	public void setT(double t) {
		T = t;
	}
	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	public double getQ() {
		return q;
	}
	public void setQ(double q) {
		this.q = q;
	}
	public double getVol() {
		return vol;
	}
	public void setVol(double vol) {
		this.vol = vol;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	private  static double CND(double x) {

		      // coefficients obtained
		      double a1 = 0.31938153;
		      double a2 = -0.356563782;
		      double a3 = 1.781477937;
		      double a4 = -1.821255978;
		      double a5 = 1.330274429;

		      double L = Math.abs(x);
		      double K = 1 / (1 + 0.2316419 * L);
		      double res = 1 - 1 / Math.sqrt(2 * Math.PI) * Math.exp(-Math.pow(L,2) / 2) * (a1 * K + a2 * Math.pow(K,2) + a3 * Math.pow(K,3) + a4 * Math.pow(K,4) + a5 * Math.pow(K,5));
		      if (x<0)
		         // if x negative, then reverse approximation by applying 1-x
		         res = 1 - res;
		      return res;
		   }
	   // default constructor
	   public BSOption(){}

	   // defined constructor
	   public BSOption(double s, double k, double t, double r, double q, double vol, double price, char type) {
	      this.S = s;
	      this.K = k;
	      this.T = t;
	      this.r = r;
	      this.q = q;
	      this.vol = vol;
	      this.price = price;
	      this.type = type;
	   }

	   public double computePrice() {
	      double dplus = (Math.log(S / K) + (r - q + Math.pow(vol,2) / 2) * T) / (vol * Math.sqrt(T));
	      double dminus = dplus - vol * Math.sqrt(T);
	      double price;
	      if (type == 'c') price = S * CND(dplus) * Math.exp(-q * T) - K * CND(dminus) * Math.exp(-r * T); // price of a call option
	      else price = S * -1 * CND(-dplus) * Math.exp(-q * T) + K * CND(-dminus) * Math.exp(-r * T);             // price of a put option
	      return price;
	   }
	   public double computePriceVol(double v) {
		      double dplus = (Math.log(S / K) + (r - q + Math.pow(v,2) / 2) * T) / (v * Math.sqrt(T));
		      double dminus = dplus - v * Math.sqrt(T);
		      double price;
		      if (type == 'c') price = S * CND(dplus) * Math.exp(-q * T) - K * CND(dminus) * Math.exp(-r * T); // price of a call option
		      else price = S * -1 * CND(-dplus) * Math.exp(-q * T) + K * CND(-dminus) * Math.exp(-r * T);             // price of a put option
		      return price;
		   }
	   public static double ND(double x) {
		      return 1/Math.sqrt(2 * Math.PI) * Math.exp(-Math.pow(x,2)/2);
		   }
	   public double ImpliedVol_NewtonRaphson(double spotPrice, double guess, int maxIter, double epsilon) {
		      int i = 0;
		      double X2 = guess;
		      double p = computePriceVol(X2) - spotPrice;
		      while (i < maxIter && Math.abs(p) > epsilon) {
		         double dplus = (Math.log(S / K) + (r - q + Math.pow(X2,2) / 2) * T) / (X2* Math.sqrt(T));
		         X2 = X2 - (computePriceVol(X2) - spotPrice) / (S*Math.exp(-q*T)*ND(dplus)*Math.sqrt(T)); // divide by vega (the derivative of price regaring volatility)
		         p = computePriceVol(X2) - spotPrice;
		         i++;
		      }
		      return X2;
		   }
	   
	}

