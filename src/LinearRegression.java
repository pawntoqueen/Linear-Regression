public class LinearRegression {

    static String[] features = {"Age", "Income", "NumberOfCards", "Score"};

    static double[][] dataset = {{35,22,63,59,25,41},
            {35,50,200,170,40,99},
            {3,2,1,1,4,2},
            {90,20,30,10,80,46}};

    static double max_val = 0;
    static double min_val = Integer.MAX_VALUE;

    public static double[] katsayi_bul( double[] mean_features, double[] sum_x_y, double[] sum_x_x){
        int n = dataset.length;
        double[] katsayilar = new double[dataset.length-1];

        for (int i = 0; i < katsayilar.length; i++) {
            katsayilar[i] = (-n * mean_features[i] * mean_features[mean_features.length-1] + sum_x_y[i])
                    / (-n * mean_features[i] * mean_features[mean_features.length-1] + sum_x_x[i]);
        }
        return katsayilar;
    }

    public static double[] predict_value(double b0, double[] katsayilar, double[][] normalize_dataset_){
        double y = b0;
        double[] output = new double[normalize_dataset_[0].length]; //6

        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < katsayilar.length; j++) {
                y += katsayilar[j] * normalize_dataset_[j][j];
            }
            output[i] = y;
        }
return output;

    }


    public static void main(String[] args) {
        double[][] normalize_dataset_ = normalization(dataset);
        double[][] x_y_dataset = x_y_multiplication(normalize_dataset_);
        double[][] x_x_dataset = x_x_multiplication(normalize_dataset_);

        double[] mean_features = new double[dataset.length];
        double[] sum_x_y = new double[dataset.length-1];
        double[] sum_x_x = new double[dataset.length-1];


        for (int i = 0; i < mean_features.length; i++) {
            mean_features[i] = mean(normalize_dataset_[i]);
        }
        for (int i = 0; i < sum_x_y.length; i++) {
            sum_x_y[i] = sum(x_y_dataset[i]);
            sum_x_x[i] = sum(x_x_dataset[i]);
        }

        double[] katsayilar = katsayi_bul(mean_features,sum_x_y,sum_x_x);


        System.out.println("normalized array");
        for (double[] item : normalize_dataset_) {
            for (double v : item) {
                System.out.format("\t%.4f", v);
            }
            System.out.println();
        }

        System.out.println("x*y array");
        for (double[] value : x_y_dataset) {
            for (double v : value) {
                System.out.format("\t%.4f", v);
            }
            System.out.println();
        }



        System.out.println("x*x array");
        for (double[] doubles : x_x_dataset) {
            for (double aDouble : doubles) {
                System.out.format("\t%.4f", aDouble);
            }
            System.out.println();
        }

        System.out.println("mean features");
        for (double mean_feature : mean_features) {
            System.out.format("\t%.4f", mean_feature);
        }
        System.out.println();


        System.out.println("sum x*y values");
        for (double x_y_data : sum_x_y) {
            System.out.format("\t%.4f", x_y_data);
        }
        System.out.println();


        System.out.println("sum x*x values");
        for (double x_x_data : sum_x_x) {
            System.out.format("\t%.4f", x_x_data);
        }
        System.out.println();

        System.out.println("katsayilar");
        for (double katsayi : katsayilar) {
            System.out.format("\t%.4f", katsayi);
        }
        System.out.println();

        System.out.println("b0:");
        double b0 = mean_features[mean_features.length-1] ;
        for (int i = 0; i < katsayilar.length; i++) {
            b0 -= katsayilar[i] * mean_features[i];
        }
        System.out.println(b0);

        double[] predict_values = predict_value(b0,katsayilar,normalize_dataset_);
        System.out.println("predict values");
        for (double pv : predict_values) {
            System.out.format("\t%.4f", pv);
        }
        System.out.println();

        double mae = 0;
        for (int i = 0; i < normalize_dataset_[0].length; i++) {
           double ai =  normalize_dataset_[normalize_dataset_.length-1][i];
           double fi = predict_values[i];
           mae += Math.abs(ai-fi);
        }

        System.out.println("MAE: "+mae/predict_values.length);



    }


    public static  double[][] normalization(double[][] dataset){
        double[][] normalized_dataset = new double[dataset.length][dataset[0].length];
        for (int i = 0; i < dataset.length; i++) {

            find_maxandmin(dataset[i]);
            for (int j = 0; j < dataset[i].length; j++) {
                normalized_dataset[i][j] = (dataset[i][j] - min_val) / (max_val - min_val);
            }
        }
        return normalized_dataset;
    }

    public static  double[][] x_y_multiplication(double[][] dataset){
        double[][] normalized_dataset = new double[dataset.length][dataset[0].length];
        for (int i = 0; i < dataset.length-1; i++) {

            find_maxandmin(dataset[i]);
            for (int j = 0; j < dataset[i].length; j++) {
                normalized_dataset[i][j] = (dataset[i][j]*dataset[dataset.length-1][j]);
            }
        }
        return normalized_dataset;
    }


    public static  double[][] x_x_multiplication(double[][] dataset){
        double[][] normalized_dataset = new double[dataset.length][dataset[0].length];
        for (int i = 0; i < dataset.length-1; i++) {

            find_maxandmin(dataset[i]);
            for (int j = 0; j < dataset[i].length; j++) {
                normalized_dataset[i][j] = (dataset[i][j]*dataset[i][j]);
            }
        }
        return normalized_dataset;
    }

    public static void find_maxandmin(double[] dataset_column){
        max_val = 0;
        min_val = Integer.MAX_VALUE;

        for (double num: dataset_column) {
            if (num > max_val) {
                max_val = num;
            }
            if (num < min_val) {
                min_val = num;
            }
        }
    }

    public static double mean(double[] dataset_column){
        double sum = 0.0;
        for (double num: dataset_column) {
            sum += num;
        }
        return sum/dataset_column.length;
    }

    public static double sum(double[] dataset_column){
        double sum = 0.0;
        for (double num: dataset_column) {
            sum += num;
        }
        return sum;
    }

}
