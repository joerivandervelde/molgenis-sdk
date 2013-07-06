<#macro plugins_search_SearchPlugin screen>
<!-- normally you make one big form for the whole plugin-->
<form method="post" enctype="multipart/form-data" name="${screen.name}">
	<!--needed in every form: to redirect the request to the right screen-->
	<input type="hidden" name="__target" value="${screen.name}"" />
	<!--needed in every form: to define the action. This can be set by the submit button-->
	<input type="hidden" name="__action" />
	
<!-- this shows a title and border -->
	<div class="formscreen">
		<div class="form_header" id="${screen.getName()}">
		${screen.label}
		</div>
		
		<#--optional: mechanism to show messages-->
		<#list screen.getMessages() as message>
			<#if message.success>
		<p class="successmessage">${message.text}</p>
			<#else>
		<p class="errormessage">${message.text}</p>
			</#if>
		</#list>
		
		<div class="screenbody">
			<div class="screenpadding">	
<#--begin your plugin-->	

<#--${screen.hello.toHtml()} ${screen.submit.toHtml()}-->



<div class="container-fluid">
	<div class="row-fluid">
		<div class="span3">

				
				<fieldset>
					<label>Chromosome</label>
					<select id="chrom" name="chrom">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="X">X</option>
					</select>
				</fieldset>	
			</div>
			
			<div class="span3">
				<fieldset>
					<label>Start bp position</label>
					<input type="text" id="start" name="start" placeholder="begin of window, from 0">
				</fieldset>	
			</div>
				
			<div class="span3">
				<fieldset>
					<label>Stop bp position</label>
					<input type="text" id="stop" name="stop" placeholder="end of window, up to ~250000000">
				</fieldset>
			</div>
				
			<div class="span3">
				<fieldset>
					<label>&nbsp;</label>
					<button type="submit" class="btn" id="Find" onclick="$(this).closest('form').find('input[name=__action]').val('Find');" />Find</button>
				</fieldset>
			</div>
				
		</div>
	</div>
</div>
	
	<#if screen.results??>
	
	<table class="table table-condensed table-striped">
		 <caption><h2>Results</h2></caption>
			<thead>
				<tr>
					<th>Chr</th>
					<th>BpPos</th>
					<th>RsID</th>
					<th>Ref</th>
					<th>Alt</th>
					<th>Qual</th>
					<th>Info</th>
				</tr>
			</thead>
			<tbody>
			<#list screen.results as variant>
				<tr>
					<td>${variant.chrom}</td>
					<td>${variant.pos?c}</td>
					<td>${variant.rsid}</td>
					<td>${variant.ref}</td>
					<td>${variant.alt}</td>
					<td>${variant.qual}</td>
					<td>${variant.info}</td>
				</tr>
			</#list>
			</tbody>
	
	</table>
	
	</#if>
			</div>
		</div>
	</div>
</form>
</#macro>
