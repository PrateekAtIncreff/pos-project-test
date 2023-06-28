
function getSalesReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/reports/sales";
}


function getSalesList(event) {
    var $form = $("#sales-form");
    var json = toJson($form);
    var url = getSalesReportUrl();
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        headers: {
        "Content-Type": "application/json",
    },
    success: function (response) {
        displaySalesReportList(response);
    },
    error: handleAjaxError,
  });

  return false;
}

let initialData = [];
//UI DISPLAY METHODS
//TODO display for given brand category combination
function displaySalesReportList(data){
	var $tbody = $('#brand-report-table').find('tbody');
	$tbody.empty();
	initialData = data;
	for(var i in data){
		var e = data[i];
		console.log(e);
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	if(data.length < 1){
	    var row = '<tr><td>No transaction for given timeframe found</td></tr>';
	    $tbody.append(row);
	}
	$("#apply-brand-filter").removeAttr("disabled");
}


function validateDate(input) {
  var dateFormat = /^\d{4}-\d{2}-\d{2}$/;
  var today = new Date();
  var inputDate = new Date(input.value);
     if (inputDate > today) {
        alert("Input date cannot be after today's date.");
    input.value = "";
  } else {
    input.setCustomValidity("");
  }
}
//INITIALIZATION CODE
function init() {
   $("#apply-filter").click(getSalesList);
    var dateInput = document.getElementById("inputSD");
    var dateInput2 = document.getElementById("inputED");
    var today = new Date();
    dateInput.setAttribute("max", today.toISOString().substring(0, 10));
    dateInput2.setAttribute("max", today.toISOString().substring(0, 10));
 }
$(document).ready(init);

