function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("tabel1");
	  switching = true;
	  // Set the sorting direction to ascending:
	  dir = "asc"; 
	  /* Make a loop that will continue until
	  no switching has been done: */
	  while (switching) {
	    // Start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /* Loop through all table rows (except the
	    first, which contains table headers): */
	    for (i = 1; i < (rows.length - 1); i++) {
	      // Start by saying there should be no switching:
	      shouldSwitch = false;
	      /* Get the two elements you want to compare,
	      one from current row and one from the next: */
	      x = rows[i].getElementsByTagName("TD")[n];
	      y = rows[i + 1].getElementsByTagName("TD")[n];
	      /* Check if the two rows should switch place,
	      based on the direction, asc or desc: */
	      if (dir == "asc") {
	        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      } else if (dir == "desc") {
	        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      }
	    }
	    if (shouldSwitch) {
	      /* If a switch has been marked, make the switch
	      and mark that a switch has been done: */
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	      // Each time a switch is done, increase this count by 1:
	      switchcount ++; 
	    } else {
	      /* If no switching has been done AND the direction is "asc",
	      set the direction to "desc" and run the while loop again. */
	      if (switchcount == 0 && dir == "asc") {
	        dir = "desc";
	        switching = true;
	      }
	    }
	  }
	}


$(document).ready(function(){
    var next = 1;
    $(".add-more").click(function(e){
        e.preventDefault();
        var addto = "#multifield" + next;
        var addRemove = "#multifield" + (next);
        next = next + 1;
        var newIn = '<div class="row" id="multifield' + next +'">' +
        			'<div class="column"><input autocomplete="off" class="input form-control" id="numepiesa' + next + '"placeholder="Nume Piesa" name="numepiesa' + next + '" type="text" style="margin-left:3px"></div>' +
        			'<div class="column"><input autocomplete="off" class="input form-control" style="margin-left:5px" placeholder="Producator" id="producator' + next + '" name="producator' + next + '" type="text"></div>' +
        			'<div class="col-xs-4"><input autocomplete="off" class="input form-control" placeholder="Pret" style="margin-left:10px" id="pret' + next + '" name="pret' + next + '" type="text"></div></div>';
        var newInput = $(newIn);
        var removeBtn = '<button id="remove' + (next - 1) + '" class="btn btn-danger remove-me" style="margin-bottom:10px;margin-top:5px">Sterge oferta de mai sus</button>';
        var removeButton = $(removeBtn);
        $(addto).after(newInput);
        $(addRemove).after(removeButton);
//        $("#field" + next).attr('data-source',$(addto).attr('data-source'));
        $("#count").val(next);  
        
            $('.remove-me').click(function(e){
                e.preventDefault();
                var fieldNum = this.id.charAt(this.id.length-1);
                var pretID = "#pret" + fieldNum;
                $(this).remove();
                $(pretID).remove();
                
                var producatorID = "#producator" + fieldNum;
                $(this).remove();
                $(producatorID).remove();
                
                var numepiesaID = "#numepiesa" + fieldNum;
                $(this).remove();
                $(numepiesaID).remove();
            });
    });
    

    
});