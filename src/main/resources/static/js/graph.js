var docPdf;
var successClose = function(response) {
	  console.log("pdf string service call response : " + response);
	  window.close();
};
var graphPayload = function(){
	var graphPayload = sessionStorage.getItem("paramsForGraph");
	  if(!graphPayload){
		  graphPayload = {};
	  }
	  else{
		  graphPayload = JSON.parse(graphPayload);
	  }
	  return graphPayload;
};
var getStringDateTime = function() {
	  var current_datetime = new Date();
	  var strDateTime = current_datetime.getDate() + "-" + (current_datetime.getMonth() + 1) + "-" + current_datetime.getFullYear() + " ";
	  var hours = current_datetime.getHours();
	  var minutes = current_datetime.getMinutes();
	  var ampm = hours >= 12 ? 'PM' : 'AM';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  hours = hours < 10 ? '0' + hours : hours;
	  minutes = minutes < 10 ? '0' + minutes : minutes;
	  return strDateTime + hours + '-' + minutes + "-" + current_datetime.getSeconds() + ' ' + ampm;
  };
  var createPdfDoc = function(urlArray) {
	  var doc = new jsPDF('p', 'pt', 'a4', false);
	  var yAxis = 40;
	  doc.setFontSize(30);
	  doc.text(60, yAxis, 'Peripheral Automation Test Report');
	  var textWidth = doc.getTextWidth('Peripheral Automation Test Report');
	  doc.line(60, yAxis + 1, 60 + textWidth, yAxis + 1);
	  yAxis = 50;
	  for (var i = 0; i < urlArray.length; i++) {
		  if (i % 2 == 0 && i != 0) {
			  /* I want only two images in a page */
			  doc.addPage(); /* Adds a new page*/
			  yAxis = 30; /* Re-initializes the value of yAxis for newly added page*/
		  }
		  doc.addImage(urlArray[i], 'png', 40, yAxis, 530, 350);
		  yAxis = yAxis + 360; /* Update yAxis */
	  }
	  return doc;
  };
  
   var sendPdfData = function(docPdf) {
	   var base64 = docPdf.output('datauristring').replace("data:application/pdf;base64,", "");
	   ajaxCall(uriFixedPath + "pdfGenerate", "POST", {
		   "base64String": base64
	   }, successClose, failure);  
   };
   
   var createBarChart = function(barChartId, response, optionsForGraph){
	   var barChart;
		  var testDataList;
		  var tempTestDataList = response;
		  testDataList = [];
		  var columns = ['Execution Date'];
		  var sheetCount = 0;
		  var viewsArray = [0];
		  for (var i = 0; i < tempTestDataList.length; i++) {
			  var excelName = tempTestDataList[i].inputExcelName;
			  if (!columns.includes(excelName)) {
				  sheetCount++;
				  columns.push(excelName);
				  var columnObject = {
					  calc: "stringify",
					  sourceColumn: sheetCount,
					  type: "string",
					  role: "annotation"
				  };
				  viewsArray.push(sheetCount);
				  viewsArray.push(columnObject);
			  }
		  }
		  testDataList.push(columns);
		  var date;
		  var testData = [];
		  for (var i = 0; i < tempTestDataList.length; i++) {
			  if (date != tempTestDataList[i].date) {
				  date = tempTestDataList[i].date;
				  if (testData.length) {
					  testDataList.push(testData);
				  }
				  testData = [date];
				  Array.prototype.push.apply(testData, new Array(columns.length - 1).fill(0));
			  }
			  var indexOfExcel = columns.indexOf(tempTestDataList[i].inputExcelName);
			  testData[indexOfExcel] = tempTestDataList[i].testCaseCount;
		  }
		  if (testData.length) {
			  testDataList.push(testData);
		  }
		  var data = google.visualization.arrayToDataTable(testDataList);

		  var view = new google.visualization.DataView(data);
		  view.setColumns(viewsArray);
		  barChart = new google.visualization.ColumnChart(document.getElementById(barChartId));
		  barChart.draw(view, optionsForGraph);
		  return barChart;
   };
  $(document).off('click', '#downloadReport').on('click','#downloadReport',function() {
	  if(docPdf){
		  docPdf.save("PeripheralReport_" + getStringDateTime() + ".pdf");
	  }
	  else{
		  alert("No data is availaible!");
	  }
	});

  google.charts.load('current', {
	  'packages': ['corechart']
  }).then(function() {
	  var successForTotal = function(response) {
		  var imageUriArray = [];
		  var options ;
		  if(response.length){
			  options = {
					  title: 'Total Executed Test Cases Trend',
					  hAxis: {
						  title: 'Execution Date'
					  },
					  vAxis: {
						  title: 'Total no. of test cases'
					  }
				  };
			  $("#barchart-total").show();
			  imageUriArray.push(createBarChart("barchart-total", response, options).getImageURI());
		  }
		  var successForPass = function(response) {
			  if(response.length){
				  options = {
						  title: 'Success Trend',
						  hAxis: {
							  title: 'Execution Date'
						  },
						  vAxis: {
							  title: 'No. of test case passed'
						  }
					  };
				  $("#barchart-success").show();
				  imageUriArray.push(createBarChart("barchart-success", response, options).getImageURI());
			  }
			  var successForFail = function(response) {
				  if(response.length){
					  options = {
							  title: 'Failure Trend',
							  hAxis: {
								  title: 'Execution Date'
							  },
							  vAxis: {
								  title: 'No. of test case failed'
							  }
						  };
					  $("#barchart-failure").show();
					  imageUriArray.push(createBarChart("barchart-failure", response, options).getImageURI());
				  }
				  if(imageUriArray.length){
					  docPdf = createPdfDoc(imageUriArray);
					  if(!sessionStorage.getItem("paramsForGraph")){
						  sendPdfData(docPdf);           
					  }
				  }
				  else{
					  $("#downloadReport").attr("disabled", true);
				  }
			  };
			  ajaxCall(uriFixedPath + "graphDataFail", "POST", graphPayload(), successForFail, failure);
		  };
		  ajaxCall(uriFixedPath + "graphDataPass", "POST", graphPayload(), successForPass, failure);
	  };
	  ajaxCall(uriFixedPath + "graphDataExecutedCount", "POST", graphPayload(), successForTotal, failure);
  });