package com.example.swapPricer;

public class IR {
    private double notional; // notional
    private double K; // fixed rate IRS
    private double alpha; // daycount factor
    private double sigma; // fwd rates volatility
    private double dT;
    private int N; // number forward rates
    private int M; // number of simulations
    public double sumPV = 0.0; //Sum of Present value of the cash flows
    public double PV = 0.0; //Present value of the Interest rate SWAP

    public IR() {
        this.notional = 0;
        this.K = 0.05;
        this.alpha = 0.5;
        this.sigma = 0.001;
        this.dT = 0.5;
        this.N = 11;
        this.M = 10000;
    }

    public IR(double notional, double K, double alpha, double sigma, double dt, int N, int M){
        this.notional = notional;
        this.K = K;
        this.alpha = alpha;
        this.sigma = sigma;
        this.dT = dt;
        this.N = N;
        this.M = M;
    }

    public static double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public IRresults runLIBORsimulations() {
        Double[][] L = new Double[this.N + 1][this.N + 1]; //forward rates
        Double[][] D = new Double[this.N + 2][this.N + 2]; //discount factors
     
        Double[] dW = new Double[this.N+1]; // discount factors
        Double[] FV = new Double[this.N+2]; // future value payment
        Double[] FVprime = new Double[this.N + 2]; // numeraire-rebased FV payment
        Double[] V = new Double[this.M]; // simulation payoff
        double drift_sum = 0.0; // discount factors
        double df_prod = 1.0; // future value payment
        double sumPV = 0.0; // numeraire-rebased FV payment
        double PV = 0.0; // simulation payoff

        //STEP 2: Initialise spot rates
        L[0][0] = getRandomNumber(0.5552, 0.5553);
        L[1][0] = getRandomNumber(0.5553, 0.5554);
        L[2][0] = getRandomNumber(0.5554, 0.5555);
        L[3][0] = getRandomNumber(0.5555, 0.5556);
        L[4][0] = getRandomNumber(0.5556, 0.5557);
        L[5][0] = getRandomNumber(0.5557, 0.5558);
        L[6][0] = getRandomNumber(0.5558, 0.5559);
        L[7][0] = getRandomNumber(0.5559, 0.5560);
        L[8][0] = getRandomNumber(0.5560, 0.5561);
        L[9][0] = getRandomNumber(0.5561, 0.5562);
        L[10][0] = getRandomNumber(0.5562, 0.5563);

        // start Main Monte Carlo loop simulation
        for(int nsim = 0; nsim < this.M; ++nsim){
            //STEP 3: BROWNIAN MOTION INCREMENTS
            dW[1] = Math.sqrt(this.dT)*Random.operator();
            dW[2] = Math.sqrt(this.dT)*Random.operator();
            dW[3] = Math.sqrt(this.dT)*Random.operator(); 
            dW[4] = Math.sqrt(this.dT)*Random.operator();  
            dW[5] = Math.sqrt(this.dT)*Random.operator();     
            dW[6] = Math.sqrt(this.dT)*Random.operator();
            dW[7] = Math.sqrt(this.dT)*Random.operator();
            dW[8] = Math.sqrt(this.dT)*Random.operator();
            dW[9] = Math.sqrt(this.dT)*Random.operator();
            dW[10] = Math.sqrt(this.dT)*Random.operator();

            // STEP 4: COMPUTE FORWARD RATES TABLEAU

            for (int n = 0; n < this.N; ++n)
            {
                for (int i = n + 1; i < this.N + 1; ++i)
                {
                    if(L[i][n] == null) {
                        break;
                    }
                    drift_sum = 0.0;
                    for (int k = i + 1; k < this.N + 1; ++k)
                    {
                        if(L[k][n] == null) {
                            break;
                        }
                        drift_sum += (this.alpha*this.sigma*L[k][n]) / (1 + this.alpha*L[k][n]);
                    }
                    L[i][n + 1] = L[i][n] * Math.exp((-drift_sum*this.sigma - 0.5*this.sigma*this.sigma)*this.dT + this.sigma* dW[n + 1]); // cout <<“L: i= “ << i <<“, n+1 = “ << n+1 “ << L[i][n+1] << “\n”;
                }
            }
            // STEP 5: COMPUTE DISCOUNT RATES TABLEAU

            for (int n = 0; n < N + 1; ++n)
            {
                for (int i = n + 1; i < N + 2; ++i)
                {
                    df_prod = 1.0;
                    for (int k = n; k < i; k++)
                    {
                        if(L[k][n] == null) {
                            break;
                        }
                        df_prod *= 1 / (1 + this.alpha*L[k][n]);
                    }
                    D[i][n] = df_prod;
                }
            }

            // STEP 6: COMPUTE EFFECTIVE FV PAYMENTS

            FV[1] = this.notional*this.alpha*(L[0][0] - this.K);
            FV[2] = this.notional*this.alpha*(L[1][1] - this.K);
            FV[3] = this.notional*this.alpha*(L[2][2] - this.K);
            FV[4] = this.notional*this.alpha*(L[3][3] - this.K);
            FV[5] = this.notional*this.alpha*(L[4][4] - this.K);
            FV[6] = this.notional*this.alpha*(L[5][5] - this.K);
            FV[7] = this.notional*this.alpha*(L[6][6] - this.K);
            FV[8] = this.notional*this.alpha*(L[7][7] - this.K);
            FV[9] = this.notional*this.alpha*(L[8][8] - this.K);
            FV[10] = this.notional*this.alpha*(L[9][9] - this.K);
            FV[11] = this.notional*this.alpha*(L[10][10] - this.K);


            // STEP 7: COMPUTE NUMERAIRE-REBASED PAYMENT
            FVprime[1] = FV[1] * D[1][0] / D[4][0];
            FVprime[2] = FV[2] * D[2][1] / D[4][1];
            FVprime[3] = FV[3] * D[3][2] / D[4][2];
            FVprime[4] = FV[4] * D[4][3] / D[4][3];
            FVprime[5] = FV[5] * D[5][4] / D[5][4];
            FVprime[6] = FV[6] * D[6][5] / D[6][5];
            FVprime[7] = FV[7] * D[7][6] / D[7][6];
            FVprime[8] = FV[8] * D[8][7] / D[8][7];
            FVprime[9] = FV[9] * D[9][8] / D[9][8];
            FVprime[10] = FV[10] * D[10][9] / D[10][9];
            FVprime[11] = FV[11] * D[11][10] / D[11][10];

            // STEP 8: COMPUTE IRS NPV
            V[nsim] = FVprime[1] * D[1][0] + FVprime[2] * D[2][0] + FVprime[3] * D[3][0] + FVprime[4] * D[4][0] + 
            FVprime[5] * D[5][0] + FVprime[6] * D[6][0] + FVprime[7] * D[7][0] + FVprime[8] * D[8][0] + FVprime[9] * D[9][0]
            + FVprime[10] * D[10][0] + FVprime[11] * D[11][0];
	    }   
        // end main MC loop

        // STEP 9: COMPUTE DISCOUNTED EXPECTED PAYOFF

        sumPV = 0.0;
        for (int nsim = 0; nsim < M; nsim++)
        {
            sumPV += V[nsim];
        }

        PV = sumPV / this.M;

        IRresults results = new IRresults(V, PV, L[10][0]);

        return results;
    }
}
