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
					<#list screen.chromOpts as chr>
						<option value="${chr}" <#if screen.chrom?? && screen.chrom == chr>SELECTED</#if>>${chr}</option>
					</#list>
					</select>
				</fieldset>	
			</div>
			
			<div class="span3">
				<fieldset>
					<label>Start bp position</label>
					<input type="text" id="start" name="start" <#if screen.start??>value="${screen.start?c}"</#if> placeholder="begin of window, from 0">
				</fieldset>	
			</div>
				
			<div class="span3">
				<fieldset>
					<label>Stop bp position</label>
					<input type="text" id="stop" name="stop" <#if screen.stop??>value="${screen.stop?c}"</#if> placeholder="end of window, up to ~250000000">
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
	
	<#if screen.results?? && screen.results?size gt 0>
	
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
