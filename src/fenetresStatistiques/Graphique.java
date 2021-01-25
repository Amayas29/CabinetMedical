package fenetresStatistiques;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graphique extends JPanel {

	private static final long serialVersionUID = 5494330284364415430L;

	private String titre;

	/** Nom de l'axe des Y */
	private String ordonnee;	
	/** Nom de l'axe des X */
	private String abscisse;
	/** La liste des donnes */
	private List<Float> valeurs;
	/** Le nom des attributs */
	private List<String> series;
	/** Nom des colonnes*/
	private List<String> categories;
	/** Listes des points*/
	private List<Integer> points; 

	private boolean legende;
	private Color couleurFond;

	public static Graphique getHistorgramme(String titre, String ordonnee, String abscisse,Color couleurFond,List<Float> valeurs,
			List<String> series,List<String> categories) {
		Graphique panel = new Graphique(titre, ordonnee, abscisse, couleurFond,valeurs, series, categories, true,null);
		JFreeChart chart = ChartFactory.createBarChart(
				panel.getTitre(),   					
				panel.getAbscisse(),					
				panel.getOrdonnee(),   				
				genererDataHistogramme(panel.getValeurs(), panel.getSeries(), panel.getCategories()),   				
				PlotOrientation.VERTICAL, 	
				panel.isLegende(),                    
				true,                     	
				false                     	
				);

		chart.setBackgroundPaint(panel.getCouleurFond());
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRangeGridlinePaint(new Color(250, 239, 197));
		plot.setBackgroundPaint(new Color(57, 113, 177));
		plot.setBackgroundAlpha(0.9f);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);		

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setFillZoomRectangle(false);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		chartPanel.setMouseZoomable(false);
		chartPanel.setBorder(null);

		panel.add(chartPanel);
		return panel;
	}

	public static Graphique getCirulaire(String titre, Color couleurFond,List<Float> valeurs,List<String> series) {
		Graphique panel = new Graphique(titre, "", "", couleurFond,valeurs, series, null, true,null);
		JFreeChart chart = ChartFactory.createPieChart(      
				panel.getTitre(),
				genererDataCirculaire(valeurs, series),              
				panel.isLegende(), 
				true, 
				false);

		chart.setBackgroundPaint(panel.getCouleurFond());
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(new Color(57, 113, 177));
		plot.setBackgroundAlpha(0.9f);


		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setFillZoomRectangle(false);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		chartPanel.setMouseZoomable(false);
		chartPanel.setBorder(null);

		panel.add(chartPanel);

		return panel;
	}

	public static Graphique getCourbe(String titre, String ordonnee, String abscisse, Color couleurFond,List<Float> valeurs,
			List<Integer> points,String nomCourbe, boolean entier) {

		Graphique panel = new Graphique(titre, ordonnee, abscisse, couleurFond,valeurs, null, null, true,points);
		JFreeChart chart = ChartFactory.createLineChart(
				panel.getTitre(),   					
				panel.getAbscisse(),					
				panel.getOrdonnee(),   				
				genererDataCourbe(panel.getValeurs(), panel.getPoints(),nomCourbe),   				
				PlotOrientation.VERTICAL, 	
				panel.isLegende(),                    
				true,                     	
				false);

		chart.setBackgroundPaint(panel.getCouleurFond());
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinePaint(new Color(250, 239, 197).darker());
		plot.setBackgroundPaint(new Color(57, 113, 177));
		plot.setBackgroundAlpha(0.9f);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		if(entier) {
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		}
		else {
			float max = 0; 

			for(float val : valeurs) {
				if(val > max) {
					max = val;
				}
			}

			// new Range(Math.min(0, min - Math.max(min/4, 10))
			rangeAxis.setRange(0, max + Math.max(max/5, 10));
		}

		CategoryItemRenderer renderer = plot.getRenderer();
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.green,
				0.0f, 0.0f, new Color(0, 40, 70)); 
		renderer.setSeriesPaint(0, gp0);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setFillZoomRectangle(false);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		chartPanel.setMouseZoomable(false);
		chartPanel.setBorder(null);

		panel.add(chartPanel);
		return panel;
	}

	public static Graphique getMultiCourbe(String titre, String ordonnee, String abscisse, Color couleurFond,List<Float> valeurs,
			List<Integer> points, List<String> nomsCourbes, boolean entier, Color[] couleurs) {

		Graphique panel = new Graphique(titre, ordonnee, abscisse, couleurFond,valeurs, null, null, true,points);
		JFreeChart chart = ChartFactory.createXYLineChart(
				panel.getTitre(),   					
				panel.getAbscisse(),				 	
				panel.getOrdonnee(),   				
				genererDataMultiCourbe(panel.getValeurs(), panel.getPoints(),nomsCourbes),   				
				PlotOrientation.VERTICAL, 	
				panel.isLegende(),                    
				true,                     	
				false);

		chart.setBackgroundPaint(panel.getCouleurFond());
		final XYPlot plot = chart.getXYPlot( );

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		if(entier) {
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		}
		else {
			float max = 0; 
			float min = 0;

			for(float val : valeurs) {
				if(val > max) {
					max = val;
				}
				if(val < min) {
					min = val;
				}
			}

			rangeAxis.setRange(new Range(Math.min(0, min - Math.max(min/4, 10)), max + Math.max(max/4, 10)));
		}

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		for(int i = 0; i < nomsCourbes.size(); i++) {
			renderer.setSeriesPaint( i , couleurs[ i ] );
			renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
		}

		plot.setRenderer( renderer ); 
		plot.setRangeGridlinePaint(new Color(250, 239, 197).darker());
		plot.setBackgroundPaint(new Color(57, 113, 177));
		plot.setBackgroundAlpha(0.9f);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setFillZoomRectangle(false);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		chartPanel.setMouseZoomable(false);
		chartPanel.setBorder(null);

		panel.add(chartPanel);
		return panel;
	}

	public List<Integer> getPoints() { return points;}
	public String getTitre() {return titre;}
	public void setTitre(String titre) {this.titre = titre;}
	public String getOrdonnee() {return ordonnee;}
	public void setOrdonnee(String ordonnee) {this.ordonnee = ordonnee;}
	public String getAbscisse() {return abscisse;}
	public List<Float> getValeurs() { return valeurs;}
	public List<String> getSeries() { return series;}
	public List<String> getCategories() { return categories; }
	public Color getCouleurFond() { return couleurFond;}
	public boolean isLegende() { return legende;}

	private static DefaultCategoryDataset genererDataHistogramme( List<Float> valeurs,List<String> series,List<String> categories) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		int k = 0;
		for ( int j = 0; j < categories.size(); j++){
			for (int i = 0; i < series.size(); i++){
				data.addValue(valeurs.get(k), series.get(i), categories.get(j));
				k++;
			}
		}
		return data;
	}

	private static PieDataset genererDataCirculaire(List<Float> valeurs,List<String> series) {
		DefaultPieDataset data = new DefaultPieDataset( );
		for(int i = 0; i < series.size(); i++) {
			data.setValue(series.get(i), valeurs.get(i));
		}
		return data; 
	}

	private static DefaultCategoryDataset genererDataCourbe( List<Float> valeurs,List<Integer> points,String nomCourbe) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for (int i = 0; i < points.size(); i++) {
			data.addValue(valeurs.get(i), nomCourbe, points.get(i));
		}
		return data;
	}

	private static XYDataset genererDataMultiCourbe(List<Float> valeurs, List<Integer> points, List<String> nomsCourbes) {
		XYSeries serie;         
		final XYSeriesCollection data = new XYSeriesCollection();
		for(int i = 0; i < nomsCourbes.size(); i++) {
			serie = new XYSeries(nomsCourbes.get(i));
			for(int j = 0; j < points.size(); j++) {
				serie.add( points.get(j),valeurs.get((valeurs.size() / nomsCourbes.size()) * i + j) );
			}
			data.addSeries(serie);

		}
		return data;
	}

	private Graphique(String titre, String ordonnee, String abscisse,Color couleurFond,List<Float> valeurs, List<String> series,
			List<String> categories, boolean legende,List<Integer> points) {
		super(new GridLayout(1,0));
		this.titre = titre;
		this.ordonnee = ordonnee;
		this.abscisse = abscisse;
		this.valeurs = valeurs;
		this.series = series;
		this.categories = categories;
		this.legende = legende;
		this.couleurFond = couleurFond;
		this.points = points;
	}

}