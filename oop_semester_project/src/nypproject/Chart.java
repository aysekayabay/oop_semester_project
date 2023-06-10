package nypproject;

        
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class Chart {
	private static ArrayList<Exercise> solvedExercises = new ArrayList<>();
	private static String email;
	
	public Chart(ArrayList<Exercise> exercises, String userEmail) {
		solvedExercises= exercises;
		setEmail(userEmail);
	}
    private static CategoryDataset createDatasetForLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < solvedExercises.size(); i++) {
            dataset.addValue(solvedExercises.get(i).getAccuracyScore(), "Doğruluk Oranı", solvedExercises.get(i).getExerciseName());
            dataset.addValue(solvedExercises.get(i).getSpeedScore(), "Hız", solvedExercises.get(i).getExerciseName());
        }
        return dataset;
    }
    private static CategoryDataset createDatasetForDualBarChart1() {

        final String series1 = "Doğru Cevap Sayısı";
        final String series2 = "Yanlış Cevap Sayısı";


        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Exercise exercise : solvedExercises) {
			dataset.addValue(exercise.getCorrectAnswerCount(), series1, exercise.getExerciseName());
			dataset.addValue(exercise.getWrongAnswerCount(), series2, exercise.getExerciseName());
		
        }

        return dataset;

    }
   
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
        CategoryDataset datasetForLineChart = createDatasetForLineChart();
        CategoryDataset datasetForBarChart1 = createDatasetForDualBarChart1();



     
        JFreeChart lineChart = ChartFactory.createLineChart(
            email + " \nGelişim Grafiği",    
            "Alıştırmalar",            
            "Skor",               
            datasetForLineChart,              
            PlotOrientation.VERTICAL,
            true,                  
            true,                 
            false                
        );

        CategoryPlot linePlot = lineChart.getCategoryPlot();
        linePlot.setBackgroundPaint(Color.BLACK);
        CategoryItemRenderer lineRenderer = linePlot.getRenderer();
        lineRenderer.setStroke(new BasicStroke(2.0f)); // 2.0f değeri çizgi genişliğini belirtir
        
        CategoryAxis lineDomainAxis = linePlot.getDomainAxis();
        lineDomainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 12));
        lineDomainAxis.setLabelFont(new Font("Tahoma", Font.BOLD, 14));

        NumberAxis lineRangeAxis = (NumberAxis) linePlot.getRangeAxis();
        lineRangeAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 12));
        lineRangeAxis.setLabelFont(new Font("Tahoma", Font.BOLD, 14));

        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        lineChartPanel.setPreferredSize(new java.awt.Dimension(500, 300));

        final JFreeChart chart = ChartFactory.createBarChart(
                "Doğru/Yanlış Cevap Grafiği",        // chart title
                "Alıştırmalar",               // domain axis label
                "Miktar",                  // range axis label
                datasetForBarChart1,                 // data
                PlotOrientation.VERTICAL,
                true,                    
                true,                     
                false                    
            );
        
        chart.setBackgroundPaint(Color.white);
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.BLACK);
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.mapDatasetToRangeAxis(1, 1);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, Color.GREEN); 
        renderer.setSeriesPaint(1, Color.RED); 
        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        plot.setRenderer(1, renderer2);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
       
        JFrame frame = new JFrame("Gelişim Grafiği");
        frame.getContentPane().setBackground(Color.WHITE);
        lineChartPanel.setLayout(new BorderLayout());        
        frame.getContentPane().add(lineChartPanel, BorderLayout.NORTH);
        frame.add(chartPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        frame.setLocation(x, y);
    }
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		Chart.email = email;
	}
}