/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableCell;

import org.controlsfx.control.Notifications;

import javafx.geometry.Side;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.zavtech.morpheus.frame.DataFrame;
import com.zavtech.morpheus.frame.DataFrameSource;
import com.zavtech.morpheus.viz.chart.Chart;
import com.zavtech.morpheus.yahoo.YahooField;
import com.zavtech.morpheus.yahoo.YahooQuoteHistorySource;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.animation.Timeline;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Color;
import tn.esprit.SLTS_server.util.OptionsYahoo;
import tn.esprit.sltsclient.Utils.DataCollector;
import tn.esprit.sltsclient.Utils.FadeInTransition;
import tn.esprit.sltsclient.Utils.Navigation;
import tn.esprit.sltsclient.Utils.News;
import tn.esprit.sltsclient.Utils.NewsItem;
import tn.esprit.sltsclient.Utils.Options;
import tn.esprit.sltsclient.Utils.SentimentAnalysisWithCount;
import tn.esprit.sltsclient.Utils.StatsT;
import tn.esprit.sltsclient.Utils.Stocks;
import tn.esprit.sltsclient.Utils.mouseDrag;
import tn.esprit.sltsclient.Utils.time;
import twitter4j.TwitterException;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tn.esprit.sltsclient.Utils.exit;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.NumberAxis;

/**
 * 
 * FXML Controller class
 *
 * @author AGORA
 */
public class SearchOptionssharesController implements Initializable {
	@FXML
	private AnchorPane rootpane;
	@FXML
	private AnchorPane panecloseprices;
	@FXML
	private JFXTextField symbole;

	@FXML
	private JFXButton daily1;

	@FXML
	private JFXButton daily5;

	@FXML
	private JFXButton yearly1;

	@FXML
	private JFXButton yearly5;

	@FXML
	private JFXButton monthly6;

	@FXML
	private JFXButton monthly3;

	@FXML
	private JFXButton btngo;

	@FXML
	private Label symbolyf;

	@FXML
	private Label nameyf;

	@FXML
	private Label currencyyf;
	@FXML
    private PieChart chart1;

	@FXML
	private Label stockexchangeyf;

	@FXML
	private JFXTreeTableView<Stocks> tableviewstocks;
	@FXML
	private JFXTreeTableView<Options> tableviewcall;
	@FXML
	private JFXTreeTableView<Options> tableviewput;
	@FXML
	private JFXTreeTableView<StatsT> tableviewstats;
	@FXML
	private JFXTreeTableView<News> tableviewnews;
	OptionsYahoo o = new OptionsYahoo();
	ObservableList<Stocks> stocks;
	List<HistoricalQuote> listehq;
	Navigation nav = new Navigation();
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yy");
	private LineChart<LocalDateTime, Number> lineChart;

	@FXML
	private BarChart<String, Integer> bc;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	XYChart.Series<String, Integer> series;
	DataFrame<String, YahooField> stats;
	LinkedList<NewsItem> linews;
	public void searchforStocksbysymbol(String period, int num) {
		Stock stock = null;
		if (period.equals("none") && num == 0) {
			try {
				stock = YahooFinance.get(symbole.getText(), true);
			} catch (IOException ex) {
				nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol not found!!");
				symbole.requestFocus();
			}
		} else {
			try {
				Calendar from = Calendar.getInstance();
				Calendar to = Calendar.getInstance();
				if (period.equals("Month")) {
					from.add(Calendar.MONTH, -num);
					stock = YahooFinance.get(symbole.getText(), from, to, Interval.MONTHLY);
				} else if (period.equals("Year")) {
					from.add(Calendar.YEAR, -num);
					stock = YahooFinance.get(symbole.getText(), from, to, Interval.WEEKLY);
				} else if (period.equals("Day")) {
					from.add(Calendar.DAY_OF_YEAR, -num);
					stock = YahooFinance.get(symbole.getText(), from, to, Interval.DAILY);
				}

			} catch (IOException ex) {
				nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol not found!!");
				symbole.requestFocus();
			}
		}

		if (stock != null) {
			symbolyf.setText(stock.getSymbol());
			nameyf.setText(stock.getName());
			stockexchangeyf.setText(stock.getStockExchange());
			currencyyf.setText(stock.getCurrency());
			pupulatetableviewstocks(stock);
		}

	}

	@FXML
	void btngoclicked(ActionEvent event) {
		searchforStocksbysymbol("none", 0);
		setTableviewcall();
		setTableviewput();
		addchart();
		populstats();
		addchartstat();
		populateNews();
		populatestatnews();
	}

	private void addchartstat() {

		bc.getProperties().clear();
		bc.getData().clear();

		xAxis = new CategoryAxis();
		xAxis.setLabel("Statistic");

		yAxis = new NumberAxis();
		yAxis.setLabel("Value");
		// bc=new BarChart(xAxis, yAxis);
		series = new XYChart.Series<>();
		DataFrame<String, YahooField> statstochart = stats.cols().select(YahooField.PROFIT_MARGIN,
				YahooField.OPERATING_MARGIN, YahooField.RETURN_ON_ASSETS, YahooField.RETURN_ON_EQUITY)
				.applyDoubles(v -> {
					return v.getDouble() * 100d;
				});
		statstochart.rows().forEach(x -> {
			series.getData().add(new XYChart.Data("Profit margin", x.getValue(YahooField.PROFIT_MARGIN)));
			series.getData().add(new XYChart.Data("Operating margin", x.getValue(YahooField.OPERATING_MARGIN)));
			series.getData().add(new XYChart.Data("Return on assets", x.getValue(YahooField.RETURN_ON_ASSETS)));
			series.getData().add(new XYChart.Data("Return on equity", x.getValue(YahooField.RETURN_ON_EQUITY)));
		});
		series.setName("Profitability and Return metrics");

		bc.getData().add(series);

	}

	private void addchart() {
		if (lineChart != null) {
			lineChart.getData().clear();
			lineChart.getProperties().clear();
			// lineChart.getData().remove(0);
			lineChart.setVisible(false);

		}
		final StringConverter<LocalDateTime> STRING_CONVERTER = new StringConverter<LocalDateTime>() {
			@Override
			public String toString(LocalDateTime localDateTime) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy\nHH:mm:ss");
				return dtf.format(localDateTime);
			}

			@Override
			public LocalDateTime fromString(String s) {
				return LocalDateTime.parse(s);
			}
		};

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("close price");

		tn.esprit.sltsclient.Utils.DateAxis310 xAxis = new tn.esprit.sltsclient.Utils.DateAxis310();
		xAxis.setTickLabelFormatter(STRING_CONVERTER);

		XYChart.Series series = new XYChart.Series<LocalDateTime, Number>();
		series.setName("Date");

		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(symbole.getText() + " : close prices(last 10 years)");
		lineChart.getData().add(series);
		lineChart.setAnimated(false);
		xAxis.setTickLabelFormatter(STRING_CONVERTER);

		addData(series);
		lineChart.setCreateSymbols(false);

		lineChart.setLayoutX(1);
		lineChart.setLayoutY(46);
		lineChart.setPrefHeight(450);
		lineChart.setPrefWidth(600);

		panecloseprices.getChildren().add(lineChart);
	}

	private void addData(final XYChart.Series SERIES) {

		List<XYChart.Data<LocalDateTime, Number>> data = new ArrayList<>();

		YahooQuoteHistorySource source = DataFrameSource.lookup(YahooQuoteHistorySource.class);
		DataFrame<LocalDate, YahooField> closePrices = source.read(options -> {
			options.withStartDate(LocalDate.now().minusYears(10));
			options.withEndDate(LocalDate.now());
			options.withDividendAdjusted(true);
			options.withTicker(symbole.getText());
		}).cols().select(YahooField.PX_CLOSE);

		closePrices.rows().forEach(x -> {
			System.out.println("date : " + x.key());
			// System.out.println("Index : "+);
			LocalDate year = x.key();
			Double pr = Double.parseDouble(x.getValue(YahooField.PX_CLOSE).toString());
			data.add(new XYChart.Data<>(
					LocalDateTime.of(x.key().getYear(), x.key().getMonth(), x.key().getDayOfMonth(), 0, 0, 0), pr));

		});

		SERIES.getData().setAll(data);
	}

	private void pupulatetableviewstocks(Stock stock) {
		JFXTreeTableColumn<Stocks, String> adjclose = new JFXTreeTableColumn<>("Adj close");
		adjclose.setPrefWidth(150);
		adjclose.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
						return param.getValue().getValue().adjclose;
					}
				});

		JFXTreeTableColumn<Stocks, String> close = new JFXTreeTableColumn<>("Close");
		close.setPrefWidth(150);
		close.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
						return param.getValue().getValue().close;
					}
				});

		JFXTreeTableColumn<Stocks, String> date = new JFXTreeTableColumn<>("Date");
		date.setPrefWidth(150);
		date.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
						return param.getValue().getValue().date;
					}
				});

		JFXTreeTableColumn<Stocks, String> highh = new JFXTreeTableColumn<>("High");
		highh.setPrefWidth(150);
		highh.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
						return param.getValue().getValue().highh;
					}
				});
		JFXTreeTableColumn<Stocks, String> low = new JFXTreeTableColumn<>("Low");
		low.setPrefWidth(150);
		low.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
						return param.getValue().getValue().low;
					}
				});
		JFXTreeTableColumn<Stocks, String> open = new JFXTreeTableColumn<>("Open");
		open.setPrefWidth(150);
		open.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
						return param.getValue().getValue().open;
					}
				});
		JFXTreeTableColumn<Stocks, String> volume = new JFXTreeTableColumn<>("Volume");
		volume.setPrefWidth(150);
		volume.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
						return param.getValue().getValue().volume;
					}
				});

		/////

		stocks = FXCollections.observableArrayList();

		listehq = new ArrayList<HistoricalQuote>();
		try {
			listehq = stock.getHistory();

		} catch (IOException e) {

			e.printStackTrace();
		}

		for (HistoricalQuote historicalQuote : listehq) {

			if (historicalQuote.getClose() != null) {

				Stocks ltable = new Stocks(historicalQuote.getAdjClose().toString(),
						historicalQuote.getClose().toString(), historicalQuote.getDate().getTime().toString(),
						historicalQuote.getHigh().toString(), historicalQuote.getLow().toString(),
						historicalQuote.getOpen().toString(), historicalQuote.getVolume().toString());
				stocks.add(ltable);
			}
		}

		final TreeItem<Stocks> root;
		root = new RecursiveTreeItem<Stocks>(stocks, RecursiveTreeObject::getChildren);
		tableviewstocks.getColumns().setAll(adjclose, close, date, highh, low, open, volume);
		tableviewstocks.setRoot(root);
		tableviewstocks.setShowRoot(false);

	}

	@FXML
	void daily1Clicked(ActionEvent event) {
		if (symbole.getText().equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
			symbole.requestFocus();
		} else {
			searchforStocksbysymbol("Day", 1);

		}

	}

	@FXML
	void daily5clicked(ActionEvent event) {
		if (symbole.getText().equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
			symbole.requestFocus();
		} else {
			searchforStocksbysymbol("Day", 5);

		}

	}

	@FXML
	void monthly3clicked(ActionEvent event) {
		if (symbole.getText().equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
			symbole.requestFocus();
		} else {
			searchforStocksbysymbol("Month", 3);

		}

	}

	@FXML
	void monthly6clicked(ActionEvent event) {
		if (symbole.getText().equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
			symbole.requestFocus();
		} else {
			searchforStocksbysymbol("Month", 6);

		}

	}

	@FXML
	void yearly1clicked(ActionEvent event) {
		if (symbole.getText().equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
			symbole.requestFocus();
		} else {
			searchforStocksbysymbol("Year", 1);

		}

	}

	@FXML
	void yearly5clicked(ActionEvent event) {
		if (symbole.getText().equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
			symbole.requestFocus();
		} else {
			searchforStocksbysymbol("Year", 5);

		}

	}

	public void setTableviewcall() {
		populoptions("call");

	}

	public void setTableviewput() {
		populoptions("put");

	}

	public void populoptions(String opttype) {
		JFXTreeTableColumn<Options, String> index = new JFXTreeTableColumn<>("index");
		index.setPrefWidth(150);
		index.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().index;
					}
				});
		JFXTreeTableColumn<Options, String> ticker = new JFXTreeTableColumn<>("ticker");
		ticker.setPrefWidth(150);
		ticker.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().ticker;
					}
				});
		JFXTreeTableColumn<Options, String> optiontype = new JFXTreeTableColumn<>("optiontype");
		optiontype.setPrefWidth(150);
		optiontype.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().optiontype;
					}
				});
		JFXTreeTableColumn<Options, String> exirydate = new JFXTreeTableColumn<>("exirydate");
		exirydate.setPrefWidth(150);
		exirydate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().exirydate;
					}
				});
		JFXTreeTableColumn<Options, String> strikeprice = new JFXTreeTableColumn<>("strikeprice");
		strikeprice.setPrefWidth(150);
		strikeprice.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().strikeprice;
					}
				});
		JFXTreeTableColumn<Options, String> lastprice = new JFXTreeTableColumn<>("lastprice");
		lastprice.setPrefWidth(150);
		lastprice.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().lastprice;
					}
				});

		JFXTreeTableColumn<Options, String> change = new JFXTreeTableColumn<>("change");
		change.setPrefWidth(150);
		change.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {

						return param.getValue().getValue().change;
					}
				});

		change.setCellFactory(new Callback<TreeTableColumn<Options, String>, TreeTableCell<Options, String>>() {

			@Override
			public TreeTableCell<Options, String> call(TreeTableColumn<Options, String> param) {

				return new TreeTableCell<Options, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {

						if (!empty) {
							setText(item);
							if (item.contains("-")) {
								setTextFill(Color.RED);

							} else if (item.equals("0.0")) {
								setTextFill(Color.BLUE);

							} else {
								setTextFill(Color.GREEN);

							}
						} else {
							setText(item);
						}

					}
				};
			}
		});
		JFXTreeTableColumn<Options, String> changepercent = new JFXTreeTableColumn<>("changepercent");
		changepercent.setPrefWidth(150);
		changepercent.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().changepercent;
					}
				});
		JFXTreeTableColumn<Options, String> bid = new JFXTreeTableColumn<>("bid");
		bid.setPrefWidth(150);
		bid.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().bid;
					}
				});
		JFXTreeTableColumn<Options, String> ask = new JFXTreeTableColumn<>("ask");
		ask.setPrefWidth(150);
		ask.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().ask;
					}
				});

		JFXTreeTableColumn<Options, String> volume = new JFXTreeTableColumn<>("volume");
		volume.setPrefWidth(150);
		volume.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().volume;
					}
				});
		JFXTreeTableColumn<Options, String> openinterest = new JFXTreeTableColumn<>("openinterest");
		openinterest.setPrefWidth(150);
		openinterest.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().openinterest;
					}
				});
		JFXTreeTableColumn<Options, String> impvolatility = new JFXTreeTableColumn<>("impvolatility");
		impvolatility.setPrefWidth(150);
		impvolatility.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Options, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Options, String> param) {
						return param.getValue().getValue().impvolatility;
					}
				});

		ObservableList<Options> atts = FXCollections.observableArrayList();

		DataFrame<String, YahooField> optionQuotes;
		if (opttype.equals("call")) {

			optionQuotes = o.optionquotes(symbole.getText(), "call");
			optionQuotes.rows().forEach(

					x -> {

						String chnge = "Nan";
						if (x.getValue(YahooField.PX_CHANGE) != null) {
							chnge = x.getValue(YahooField.PX_CHANGE).toString();
						}
						Options atable = new Options(x.key(), x.getValue(YahooField.TICKER).toString(),
								x.getValue(YahooField.OPTION_TYPE).toString(),
								x.getValue(YahooField.EXPIRY_DATE).toString(),
								x.getValue(YahooField.PX_STRIKE).toString(), x.getValue(YahooField.PX_LAST).toString(),
								chnge, x.getValue(YahooField.PX_CHANGE_PERCENT).toString(),
								x.getValue(YahooField.PX_BID).toString(), x.getValue(YahooField.PX_ASK).toString(),
								x.getValue(YahooField.PX_VOLUME).toString(),
								x.getValue(YahooField.OPEN_INTEREST).toString(),
								x.getValue(YahooField.IMPLIED_VOLATILITY).toString());
						atts.add(atable);
						final TreeItem<Options> root;

						root = new RecursiveTreeItem<>(atts, RecursiveTreeObject::getChildren);
						tableviewcall.getColumns().setAll(index, ticker, optiontype, exirydate, strikeprice, lastprice,
								change, changepercent, bid, ask, volume, openinterest, impvolatility);

						tableviewcall.setRoot(root);
						tableviewcall.setShowRoot(false);
					});

		} else {
			optionQuotes = o.optionquotes(symbole.getText(), "put");
			optionQuotes.rows().forEach(

					x -> {

						Options atable = new Options(x.key(), x.getValue(YahooField.TICKER).toString(),
								x.getValue(YahooField.OPTION_TYPE).toString(),
								x.getValue(YahooField.EXPIRY_DATE).toString(),
								x.getValue(YahooField.PX_STRIKE).toString(), x.getValue(YahooField.PX_LAST).toString(),
								x.getValue(YahooField.PX_CHANGE).toString(),
								x.getValue(YahooField.PX_CHANGE_PERCENT).toString(),
								x.getValue(YahooField.PX_BID).toString(), x.getValue(YahooField.PX_ASK).toString(),
								x.getValue(YahooField.PX_VOLUME).toString(),
								x.getValue(YahooField.OPEN_INTEREST).toString(),
								x.getValue(YahooField.IMPLIED_VOLATILITY).toString());
						atts.add(atable);
						final TreeItem<Options> root;
						root = new RecursiveTreeItem<>(atts, RecursiveTreeObject::getChildren);
						tableviewput.getColumns().setAll(index, ticker, optiontype, exirydate, strikeprice, lastprice,
								change, changepercent, bid, ask, volume, openinterest, impvolatility);
						tableviewput.setRoot(root);
						tableviewput.setShowRoot(false);
					});
		}

	}

	public void populstats() {
		JFXTreeTableColumn<StatsT, String> marketcap = new JFXTreeTableColumn<>("market cap ");
		marketcap.setPrefWidth(150);
		marketcap.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().marketcap;
					}
				});
		JFXTreeTableColumn<StatsT, String> petrailing = new JFXTreeTableColumn<>("petrailing");
		petrailing.setPrefWidth(150);
		petrailing.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().petrailing;
					}
				});
		JFXTreeTableColumn<StatsT, String> pricesalesratio = new JFXTreeTableColumn<>("pricesalesratio");
		pricesalesratio.setPrefWidth(150);
		pricesalesratio.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().pricesalesratio;
					}
				});
		JFXTreeTableColumn<StatsT, String> pricebookratio = new JFXTreeTableColumn<>("pricebookratio");
		pricebookratio.setPrefWidth(150);
		pricebookratio.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().pricebookratio;
					}
				});
		JFXTreeTableColumn<StatsT, String> fiscalyearend = new JFXTreeTableColumn<>("fiscalyearend");
		fiscalyearend.setPrefWidth(150);
		fiscalyearend.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().fiscalyearend;
					}
				});
		JFXTreeTableColumn<StatsT, String> mostrecentquarter = new JFXTreeTableColumn<>("mostrecentquarter");
		mostrecentquarter.setPrefWidth(150);
		mostrecentquarter.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().mostrecentquarter;
					}
				});
		JFXTreeTableColumn<StatsT, String> profitmargin = new JFXTreeTableColumn<>("profitmargin");
		profitmargin.setPrefWidth(150);
		profitmargin.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().profitmargin;
					}
				});
		JFXTreeTableColumn<StatsT, String> operatingmargin = new JFXTreeTableColumn<>("operatingmargin");
		operatingmargin.setPrefWidth(150);
		operatingmargin.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().operatingmargin;
					}
				});
		JFXTreeTableColumn<StatsT, String> returnonasset = new JFXTreeTableColumn<>("returnonasset");
		returnonasset.setPrefWidth(150);
		returnonasset.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().returnonasset;
					}
				});
		JFXTreeTableColumn<StatsT, String> returnonequities = new JFXTreeTableColumn<>("returnonequities");
		returnonequities.setPrefWidth(150);
		returnonequities.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().returnonequities;
					}
				});
		JFXTreeTableColumn<StatsT, String> revenuetim = new JFXTreeTableColumn<>("revenuetim");
		revenuetim.setPrefWidth(150);
		revenuetim.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().revenuetim;
					}
				});
		JFXTreeTableColumn<StatsT, String> revenuepershare = new JFXTreeTableColumn<>("revenuepershare");
		revenuepershare.setPrefWidth(150);
		revenuepershare.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().revenuepershare;
					}
				});
		JFXTreeTableColumn<StatsT, String> revenuegrowthqtly = new JFXTreeTableColumn<>("revenuegrowthqtly");
		revenuegrowthqtly.setPrefWidth(150);
		revenuegrowthqtly.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().revenuegrowthqtly;
					}
				});
		JFXTreeTableColumn<StatsT, String> grossprfit = new JFXTreeTableColumn<>("grossprfit");
		grossprfit.setPrefWidth(150);
		grossprfit.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().grossprfit;
					}
				});
		JFXTreeTableColumn<StatsT, String> ebitdatim = new JFXTreeTableColumn<>("ebitdatim");
		ebitdatim.setPrefWidth(150);
		ebitdatim.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().ebitdatim;
					}
				});
		JFXTreeTableColumn<StatsT, String> epsdiluted = new JFXTreeTableColumn<>("epsdiluted");
		epsdiluted.setPrefWidth(150);
		epsdiluted.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().epsdiluted;
					}
				});
		JFXTreeTableColumn<StatsT, String> earningsgrowthqtly = new JFXTreeTableColumn<>("earningsgrowthqtly");
		earningsgrowthqtly.setPrefWidth(150);
		earningsgrowthqtly.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().earningsgrowthqtly;
					}
				});
		JFXTreeTableColumn<StatsT, String> beta = new JFXTreeTableColumn<>("beta");
		beta.setPrefWidth(150);
		beta.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().beta;
					}
				});
		JFXTreeTableColumn<StatsT, String> cashmrq = new JFXTreeTableColumn<>("cashmrq");
		cashmrq.setPrefWidth(150);
		cashmrq.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().cashmrq;
					}
				});
		JFXTreeTableColumn<StatsT, String> cashpershare = new JFXTreeTableColumn<>("cashpershare");
		cashpershare.setPrefWidth(150);
		cashpershare.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().cashpershare;
					}
				});
		JFXTreeTableColumn<StatsT, String> debtmrq = new JFXTreeTableColumn<>("debtmrq");
		debtmrq.setPrefWidth(150);
		debtmrq.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().debtmrq;
					}
				});
		JFXTreeTableColumn<StatsT, String> debtoverequitymrq = new JFXTreeTableColumn<>("debtoverequitymrq");
		debtoverequitymrq.setPrefWidth(150);
		debtoverequitymrq.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().debtoverequitymrq;
					}
				});
		JFXTreeTableColumn<StatsT, String> curretratio = new JFXTreeTableColumn<>("curretratio");
		curretratio.setPrefWidth(150);
		curretratio.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().curretratio;
					}
				});
		JFXTreeTableColumn<StatsT, String> bookvaluepershare = new JFXTreeTableColumn<>("bookvaluepershare");
		bookvaluepershare.setPrefWidth(150);
		bookvaluepershare.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().bookvaluepershare;
					}
				});
		JFXTreeTableColumn<StatsT, String> operatingcashflow = new JFXTreeTableColumn<>("operatingcashflow");
		operatingcashflow.setPrefWidth(150);
		operatingcashflow.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().operatingcashflow;
					}
				});
		JFXTreeTableColumn<StatsT, String> leveredfreecashflow = new JFXTreeTableColumn<>("leveredfreecashflow");
		leveredfreecashflow.setPrefWidth(150);
		leveredfreecashflow.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().leveredfreecashflow;
					}
				});
		JFXTreeTableColumn<StatsT, String> adv3month = new JFXTreeTableColumn<>("adv3month");
		adv3month.setPrefWidth(150);
		adv3month.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().adv3month;
					}
				});
		JFXTreeTableColumn<StatsT, String> adv10day = new JFXTreeTableColumn<>("adv10day");
		adv10day.setPrefWidth(150);
		adv10day.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().adv10day;
					}
				});
		JFXTreeTableColumn<StatsT, String> sharesoutstanding = new JFXTreeTableColumn<>("sharesoutstanding");
		sharesoutstanding.setPrefWidth(150);
		sharesoutstanding.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().sharesoutstanding;
					}
				});
		JFXTreeTableColumn<StatsT, String> sharesfloat = new JFXTreeTableColumn<>("sharesfloat");
		sharesfloat.setPrefWidth(150);
		sharesfloat.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().sharesfloat;
					}
				});
		JFXTreeTableColumn<StatsT, String> ownerpersentinsider = new JFXTreeTableColumn<>("ownerpersentinsider");
		ownerpersentinsider.setPrefWidth(150);
		ownerpersentinsider.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().ownerpersentinsider;
					}
				});
		JFXTreeTableColumn<StatsT, String> ownerpercentinstitution = new JFXTreeTableColumn<>(
				"ownerpercentinstitution");
		ownerpercentinstitution.setPrefWidth(150);
		ownerpercentinstitution.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().ownerpercentinstitution;
					}
				});
		JFXTreeTableColumn<StatsT, String> sharesshort = new JFXTreeTableColumn<>("sharesshort");
		sharesshort.setPrefWidth(150);
		sharesshort.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().sharesshort;
					}
				});
		JFXTreeTableColumn<StatsT, String> sharesshortratio = new JFXTreeTableColumn<>("sharesshortratio");
		sharesshortratio.setPrefWidth(150);
		sharesshortratio.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().sharesshortratio;
					}
				});
		JFXTreeTableColumn<StatsT, String> sharesshortprior = new JFXTreeTableColumn<>("sharesshortprior");
		sharesshortprior.setPrefWidth(150);
		sharesshortprior.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().sharesshortprior;
					}
				});
		JFXTreeTableColumn<StatsT, String> dividendfwd = new JFXTreeTableColumn<>("dividendfwd");
		dividendfwd.setPrefWidth(150);
		dividendfwd.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().dividendfwd;
					}
				});
		JFXTreeTableColumn<StatsT, String> dividendfwdyield = new JFXTreeTableColumn<>("dividendfwdyield");
		dividendfwdyield.setPrefWidth(150);
		dividendfwdyield.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().dividendfwdyield;
					}
				});
		JFXTreeTableColumn<StatsT, String> dividendtrailing = new JFXTreeTableColumn<>("dividendtrailing");
		dividendtrailing.setPrefWidth(150);
		dividendtrailing.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().dividendtrailing;
					}
				});
		JFXTreeTableColumn<StatsT, String> dividendtrailingyield = new JFXTreeTableColumn<>("dividendtrailingyield");
		dividendtrailingyield.setPrefWidth(150);
		dividendtrailingyield.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().dividendtrailingyield;
					}
				});
		JFXTreeTableColumn<StatsT, String> dividendpayoutratio = new JFXTreeTableColumn<>("dividendpayoutratio");
		dividendpayoutratio.setPrefWidth(150);
		dividendpayoutratio.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().dividendpayoutratio;
					}
				});

		JFXTreeTableColumn<StatsT, String> dividendpaydate = new JFXTreeTableColumn<>("dividendpaydate");
		dividendpaydate.setPrefWidth(150);
		dividendpaydate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().dividendpaydate;
					}
				});
		JFXTreeTableColumn<StatsT, String> dividendexdate = new JFXTreeTableColumn<>("dividendexdate");
		dividendexdate.setPrefWidth(150);
		dividendexdate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().dividendexdate;
					}
				});
		JFXTreeTableColumn<StatsT, String> lastsplitdate = new JFXTreeTableColumn<>(" lastsplitdate");
		lastsplitdate.setPrefWidth(150);
		lastsplitdate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<StatsT, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<StatsT, String> param) {
						return param.getValue().getValue().lastsplitdate;
					}
				});

		ObservableList<StatsT> atts = FXCollections.observableArrayList();

		stats = o.getstats(symbole.getText());
		stats.rows().forEach(x -> {
			// stats atable= new statsT....
			String dexdate = "Nan";
			String lastspdate = "Nan";
			if (x.getValue(YahooField.DIVIDEND_EX_DATE) != null) {
				dexdate = x.getValue(YahooField.DIVIDEND_EX_DATE).toString();
			}
			if (x.getValue(YahooField.LAST_SPLIT_DATE) != null) {
			}
			lastspdate = x.getValue(YahooField.LAST_SPLIT_DATE).toString();

			StatsT atable = new StatsT(x.getValue(YahooField.MARKET_CAP).toString(),
					x.getValue(YahooField.PE_TRAILING).toString(), x.getValue(YahooField.PRICE_SALES_RATIO).toString(),
					x.getValue(YahooField.PRICE_BOOK_RATIO).toString(),
					x.getValue(YahooField.FISCAL_YEAR_END).toString(),
					x.getValue(YahooField.MOST_RECENT_QUARTER).toString(),
					x.getValue(YahooField.PROFIT_MARGIN).toString(), x.getValue(YahooField.OPERATING_MARGIN).toString(),
					x.getValue(YahooField.RETURN_ON_ASSETS).toString(),
					x.getValue(YahooField.RETURN_ON_EQUITY).toString(), x.getValue(YahooField.REVENUE_TTM).toString(),
					x.getValue(YahooField.REVENUE_PER_SHARE).toString(),
					x.getValue(YahooField.REVENUE_GROWTH_QTLY).toString(),
					x.getValue(YahooField.GROSS_PROFIT).toString(), x.getValue(YahooField.EBITDA_TTM).toString(),
					x.getValue(YahooField.EPS_DILUTED).toString(),
					x.getValue(YahooField.EARNINGS_GRWOTH_QTLY).toString(), x.getValue(YahooField.BETA).toString(),
					x.getValue(YahooField.CASH_MRQ).toString(), x.getValue(YahooField.CASH_PER_SHARE).toString(),
					x.getValue(YahooField.DEBT_MRQ).toString(), x.getValue(YahooField.DEBT_OVER_EQUITY_MRQ).toString(),
					x.getValue(YahooField.CURRENT_RATIO).toString(),
					x.getValue(YahooField.BOOK_VALUE_PER_SHARE).toString(),
					x.getValue(YahooField.OPERATING_CASH_FLOW).toString(),
					x.getValue(YahooField.LEVERED_FREE_CASH_FLOW).toString(),
					x.getValue(YahooField.ADV_3MONTH).toString(), x.getValue(YahooField.ADV_10DAY).toString(),
					x.getValue(YahooField.SHARES_OUTSTANDING).toString(),
					x.getValue(YahooField.SHARES_FLOAT).toString(),
					x.getValue(YahooField.OWNER_PERCENT_INSIDER).toString(),
					x.getValue(YahooField.OWNER_PERCENT_INSTITUTION).toString(),
					x.getValue(YahooField.SHARES_SHORT).toString(),
					x.getValue(YahooField.SHARES_SHORT_RATIO).toString(),
					x.getValue(YahooField.SHARES_SHORT_PRIOR).toString(),
					x.getValue(YahooField.DIVIDEND_FWD).toString(),
					x.getValue(YahooField.DIVIDEND_FWD_YIELD).toString(),
					x.getValue(YahooField.DIVIDEND_TRAILING).toString(),
					x.getValue(YahooField.DIVIDEND_TRAILING_YIELD).toString(),
					x.getValue(YahooField.DIVIDEND_PAYOUT_RATIO).toString(),
					x.getValue(YahooField.DIVIDEND_PAY_DATE).toString(), dexdate, lastspdate);

			atts.add(atable);
			final TreeItem<StatsT> root;
			root = new RecursiveTreeItem<>(atts, RecursiveTreeObject::getChildren);

			tableviewstats.getColumns().setAll(marketcap, petrailing, pricesalesratio, pricebookratio, fiscalyearend,
					mostrecentquarter, profitmargin, operatingmargin, returnonasset, returnonequities, revenuetim,
					revenuepershare, revenuegrowthqtly, grossprfit, ebitdatim, epsdiluted, earningsgrowthqtly, beta,
					cashmrq, cashpershare, debtmrq, debtoverequitymrq, curretratio, bookvaluepershare,
					operatingcashflow, leveredfreecashflow, adv3month, adv10day, sharesoutstanding, sharesfloat,
					ownerpersentinsider, ownerpercentinstitution, sharesshort, sharesshortratio, sharesshortprior,
					dividendfwd, dividendfwdyield, dividendtrailing, dividendtrailingyield, dividendpayoutratio,
					dividendpaydate, dividendexdate, lastsplitdate);
			tableviewstats.setRoot(root);
			tableviewstats.setShowRoot(false);
		});

	}

	public void populateNews() {
		JFXTreeTableColumn<News, String> titlee = new JFXTreeTableColumn<>("Title");
		titlee.setPrefWidth(150);
		titlee.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<News, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<News, String> param) {
						return param.getValue().getValue().titlee;
					}
				});
		JFXTreeTableColumn<News, String> description = new JFXTreeTableColumn<>("Description");
		description.setPrefWidth(150);
		description.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<News, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<News, String> param) {
						return param.getValue().getValue().description;
					}
				});
		JFXTreeTableColumn<News, String> url = new JFXTreeTableColumn<>("Url");
		url.setPrefWidth(150);
		url.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<News, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<News, String> param) {
						return param.getValue().getValue().url;
					}
				});
		JFXTreeTableColumn<News, String> datepub = new JFXTreeTableColumn<>("Date pub");
		datepub.setPrefWidth(150);
		datepub.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<News, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<News, String> param) {
						return param.getValue().getValue().datepub;
					}
				});
		ObservableList<News> newslist = FXCollections.observableArrayList();

		DataCollector dc = new DataCollector();

		linews = dc.obtainNews(symbole.getText());
		System.out.println("Success!");

		for (int i = 0; i < linews.size(); i++) {

			System.out.println("Title : " + linews.get(i).Title);
			System.out.println("Description : " + linews.get(i).Description);
			System.out.println("Url : " + linews.get(i).URL);
			System.out.println("Date : " + linews.get(i).DatePublished);
			System.out.println("--------------------------------");
			News n = new News(linews.get(i).Title.toString(), linews.get(i).Description.toString(), linews.get(i).URL.toString(),
					linews.get(i).DatePublished.toString());
			newslist.add(n);
			final TreeItem<News> root;
			root = new RecursiveTreeItem<>(newslist, RecursiveTreeObject::getChildren);
			tableviewnews.getColumns().setAll(
					titlee, 
					description,
					url, 
					datepub);
            
			tableviewnews.setRoot(root);
			tableviewnews.setShowRoot(false);

		}
	}

	@FXML
	void gotoweb(MouseEvent event) {
		if (event.getClickCount() == 2) {
			try {
				SearchOptionssharesController
						.openWebpage(new URI(tableviewnews.getSelectionModel().getSelectedItem().getValue().url.get()));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void populatestatnews(){
		 HashMap<String, Integer> map = null;
         try {
			 map=  SentimentAnalysisWithCount.newssanalysis(linews);
		} catch (TwitterException | IOException | NamingException e) {
			e.printStackTrace();
		}
             System.out.println("positive news :"+map.get("positivenews").toString());    
             System.out.println("negative news :"+map.get("negativenews").toString());   
             
             
             int nbpositivenews = Integer.parseInt(map.get("positivenews").toString());
     		int nbnegativenews = Integer.parseInt(map.get("negativenews").toString());
int somme=nbnegativenews+nbpositivenews;
     		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
     				new PieChart.Data("Positive News", nbpositivenews),
     				new PieChart.Data("Negative News", nbnegativenews));
     		chart1 = new PieChart(pieChartData);
     		//chart1.setTitle("Comments");
     		final Label caption = new Label("");
     		caption.setTextFill(Color.DARKORANGE);
     		caption.setStyle("-fx-font: 10 arial;");
     		chart1.setMaxSize(379, 269);
     		/*chart1.setLayoutX(239);*/
     		chart1.setLayoutY(30);
     		
     		
     		for (final PieChart.Data data : chart1.getData()) {
     			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
     				@Override
     				public void handle(MouseEvent e) {
     					caption.setTranslateX(e.getSceneX());
     					caption.setTranslateY(e.getSceneY());
     					caption.setText(String.valueOf(data.getPieValue()/somme*100) + "%");
     				}
     			});
     		}
     		rootpane.getChildren().addAll(chart1, caption);
             
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}