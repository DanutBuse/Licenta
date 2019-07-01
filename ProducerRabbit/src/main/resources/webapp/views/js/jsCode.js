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


	function addMoreOffers(loopIndex){
    	var next = 0;
        var addto = "#multifield" +loopIndex+"fieldNum"+ next;
        var addRemove = "#multifield" +loopIndex+"fieldNum"+ next;
        next = next + 1;
        var newIn = '<div class="row" id="multifield' +loopIndex+'fieldNum'+ next +'">' +
        			'<div class="column"><input autocomplete="off" class="input form-control" id="numepiesa' + loopIndex+'fieldNum'+ next  + '"placeholder="Part Name" name="numepiesa' + next + '" type="text" style="margin-left:3px"></div>' +
        			'<div class="column"><input autocomplete="off" class="input form-control" style="margin-left:5px" placeholder="Producer" id="producator' + loopIndex+'fieldNum'+ next  + '" name="producator' + next + '" type="text"></div>' +
        			'<div class="col-xs-4"><input autocomplete="off" class="input form-control" placeholder="Price (euro)" style="margin-left:10px" id="pret' + loopIndex+'fieldNum'+ next  + '" name="pret' + next + '" type="text"></div></div>';
        var newInput = $(newIn);
        var removeBtn = '<button id="remove' + loopIndex+'fieldNum'+(next - 1) + '" class="btn btn-danger remove-me" style="margin-bottom:10px;margin-top:5px">Delete the above offer!</button>';
        var removeButton = $(removeBtn);
        $(addto).after(newInput);
        $(addRemove).after(removeButton);
//        $("#field" + next).attr('data-source',$(addto).attr('data-source'));
        $("#count").val(next);  
        
            $('#remove'+loopIndex+'fieldNum'+(next-1)).click(function(e){
                e.preventDefault();
                var fieldNum = this.id.charAt(this.id.length-1);
                var pretID = "#pret" + loopIndex+'fieldNum'+ next;
                $(this).remove();
                $(pretID).remove();
                
                var producatorID = "#producator" + loopIndex+'fieldNum'+ next;
                $(this).remove();
                $(producatorID).remove();
                
                var numepiesaID = "#numepiesa" + loopIndex+'fieldNum'+ next;
                $(this).remove();
                $(numepiesaID).remove();
            });
    }
    



(function() {
	'use strict';
	window.addEventListener('load', function() {
	// Fetch all the forms we want to apply custom Bootstrap validation styles to
	var forms = document.getElementsByClassName('needs-validation');
	// Loop over them and prevent submission
	var validation = Array.prototype.filter.call(forms, function(form) {
	form.addEventListener('submit', function(event) {
	if (form.checkValidity() === false) {
	event.preventDefault();
	event.stopPropagation();
	}
	form.classList.add('was-validated');
	}, false);
	});
	}, false);
	})();