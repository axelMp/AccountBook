<!DOCTYPE html>
<html>
	<header>
		<title>Cashflow according to Kiyosaki</title>
		<g:javascript src="d3.min.js" />
	</header>
	<body>
	<script>
		var widthBox = 120;
		var heightBox = 100;
		var widthForHundredPercentage = 4;
		var area = d3.select("body").append("svg").attr("width", 3.5*widthBox).attr("height", 4*heightBox);
		
		function drawBox(root,x,y,name,color) {
			var box = root.append("rect").attr("x", x).attr("y", y).attr("width", widthBox).attr("height", heightBox).style("fill", color);
			root.append("text").attr("x", x+0.2*widthBox).attr( "y",y+0.5*heightBox).text(name);
			return box;
		}
		
		function xBox(box) {
			return parseInt(box.attr("x"));
		}
		
		function yBox(box)  {
			return parseInt(box.attr("y"));
		}
		
		function guid() {
			function s4() {
				return Math.floor((1 + Math.random()) * 0x10000)
						   .toString(16)
						   .substring(1);
				}
			return "sd"+ s4() + s4() + s4();
		}
		
		function drawArrow(root,x1,y1,x2,y2,amount,strokeWidth) {
			if (0.01>strokeWidth) {
				return;
			}
			var anId = guid();		
			root.append("line").attr("x1",x1).attr("y1",y1).attr("x2",x2).attr("y2",y2).attr("stroke","black").attr("stroke-width",strokeWidth).attr("marker-end","url(#Triangle)");
			root.append("path").attr("id",anId).attr("d","M"+x1+","+y1+" L"+x2+","+y2);
			root.append("text").attr("fill","grey").append("textPath").attr("xlink:href","#"+anId).text(Math.floor(0.01*amount)+"€");
		}
		
		function defineTriangle(root) {
			root.append("defs").append("marker").attr("id","Triangle")
			.attr("viewBox","0 0 10 10")
			.attr("refX","0")
			.attr("refY","5")
			.attr("markerUnits","strokeWidth")
			.attr("markerWidth","4")
			.attr("markerHeight","3")
			.attr("orient","auto")
			.append("path").attr("d","M 0 0 L 10 5 L 0 10 z");
		}
		
		defineTriangle(area);
		var incomeBox = drawBox(area,widthBox,0,"income","red");
		var expenseBox = drawBox(area,widthBox,heightBox,"expense","yellow");
		var assetBox = drawBox(area,0.5*widthBox,2.5*heightBox,"asset","blue");
		var liabilityBox = drawBox(area,1.5*widthBox,2.5*heightBox,"liability","green");
		
		<% out << "var passiveIncome = ${passiveIncome};" %>
		<% out << "var bankToIncome = " + income.get("ASSET").getCents() +";"%>
		<% out << "var incomeToExpense = " + -1*expense.get("ASSET").getCents() +";"%>
		<% out << "var expenseTotal = incomeToExpense;" %>
		<% out << "var incomeToAsset = passiveIncome + bankToIncome - incomeToExpense;"%>
	   
	    drawArrow(area,xBox(incomeBox),yBox(incomeBox)+0.75*heightBox,0.5*widthBox+xBox(assetBox),yBox(assetBox),incomeToAsset,widthForHundredPercentage*incomeToAsset/expenseTotal);
		drawArrow(area,xBox(incomeBox)+0.5*widthBox,yBox(incomeBox)+0.75*heightBox,xBox(expenseBox)+0.5*widthBox,yBox(expenseBox)+0.2*heightBox,incomeToExpense,widthForHundredPercentage*incomeToExpense/expenseTotal);	
		drawArrow(area,0,yBox(incomeBox)+0.2*heightBox,xBox(incomeBox),yBox(incomeBox)+0.2*heightBox,bankToIncome,widthForHundredPercentage*bankToIncome/expenseTotal);		
		

		drawArrow(area,0.125*widthBox+xBox(assetBox),yBox(assetBox),xBox(incomeBox),yBox(incomeBox)+0.5*heightBox,passiveIncome,widthForHundredPercentage*passiveIncome/expenseTotal);
		drawArrow(area,xBox(expenseBox)+widthBox,yBox(expenseBox)+0.75*heightBox,xBox(expenseBox)+2*widthBox,yBox(expenseBox)+0.75*heightBox,expenseTotal,widthForHundredPercentage);
	</script>

	</body>
</html>
