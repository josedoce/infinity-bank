/**
 * Validação de entradas
 * @author josedoce
 */

function handleDelete(id) {
	let response = confirm("Confirmar a exclusão deste contado ?");
	if(response){
		window.location.href=`/delete/${id}`;
	} 
}
 
/** jquery */
$(document).ready(function(){
	const id = $('#cid');
	const labelId = $('#cidLabel');
	const amount = $('#camount');
	const labelAmount = $('#camountLabel');
	const name = $('#cname');
	const labelName = $('#cnameLabel');
	const password = $('#cpassword');
	const labelPassword = $('#cpasswordLabel');
	
	const messages = {
		default: {
		password: 'senha entre 8 e 16 caracteres',
			name: 'nome entre 4 e 16 caracteres'
		},
		colorError: '#f66151',
		errors: {
			name: 'o nome é vazio ou invalido.',
			password: 'a senha é vazia ou invalida.'
		}
	};
	
	function handleInput(input, label, defaultMessage, custom=false, values={}){
		let properties = {
			text: defaultMessage,
			borderColor: messages.colorError,
			textColor: messages.colorError,
		};
		
		if(custom){
			properties = Object.assign(properties, values);
		}

		if(label!=null){
			label.css("color", properties.textColor);
			label.text(properties.text);	
		}
		input.css("border-color", properties.borderColor);
	}
	
	function showError(input, label, defaultMessage, custom=false, values={}){
		handleInput(input, label, defaultMessage, custom, values);
	}
	
	function clearError(input, label, defaultmessage, fun = function(input, label){}){
		input.focus(function(){
			handleInput(input, label, defaultmessage, true, {
				borderColor: "",
				textColor: ""
			});
			fun(input, label);
		});
	}
	
	function validate(event, options){
		const {
			label,
			input: {
				element,
				validation: {
					min,
					max,
					fun
				}
			}
		} = options;
		if(element == null){
			throw new Error('element is null.');	
		}
		const inputLength = element.val().length;
		
		const isInvalidInput = 
		inputLength == undefined 
		|| inputLength < min.length 
		|| inputLength > max.length ;
		
		
		if(isInvalidInput) {
			event.preventDefault();
			event.stopPropagation();
					
			if(isInvalidInput && inputLength < min.length) {
				showError(element, label, min.message);	
			}
			if(isInvalidInput && inputLength > max.length) {
				showError(element, label, max.message);
			}
			fun(element, label);
			return;
		}
	}
	
	const defaultValidation = {
		label: null,
		input: {
			element: null,
			validation: {
				fun: function(input, label) {},
				min: {
					length: 4,
					message: 'É menor que 4 carecteres.',
				},
				max: {
					length: 16,
					message: 'É maior que 16 carecteres.',
				}
			}
		}
	};	
	$('#form-account-create').submit(function(event){
		defaultValidation.label = labelName;
		defaultValidation.input.element = name;
		validate(event, defaultValidation);
		
		defaultValidation.label = labelPassword;
		defaultValidation.input.element = password;
		defaultValidation.input.validation.min.message = 'É menor que 8 carecteres.';
		validate(event, defaultValidation);
	});
	
	
	$('#form-account-update').submit(function(event){
		//field id
		defaultValidation.label = labelId;
		defaultValidation.input.element = id;
		defaultValidation.input.validation.min.length = 1;
		defaultValidation.input.validation.max.length = 10;
		defaultValidation.input.validation.min.message = 'Id é menor que 1 carectere.';
		defaultValidation.input.validation.max.message = 'Id é maior que 10 carecteres.';	
		defaultValidation.input.validation.fun = function(input, label) {
			label.removeClass('hide');
		}
		validate(event, defaultValidation);
		
		//field name
		defaultValidation.label = labelName;
		defaultValidation.input.element = name;
		validate(event, defaultValidation);
		
		//filed password
		defaultValidation.label = labelPassword;
		defaultValidation.input.element = password;
		defaultValidation.input.validation.min.message = 'É menor que 8 carecteres.';
		validate(event, defaultValidation);
	});
	
	$('#form-account-amount').submit(function(event){
		//field id
		defaultValidation.label = labelId;
		defaultValidation.input.element = id;
		defaultValidation.input.validation.min.length = 1;
		defaultValidation.input.validation.max.length = 10;
		defaultValidation.input.validation.min.message = 'Id é menor que 1 carectere.';
		defaultValidation.input.validation.max.message = 'Id é maior que 10 carecteres.';	
		defaultValidation.input.validation.fun = function(input, label) {
			label.removeClass('hide');
		}
		validate(event, defaultValidation);
		
		//field amount
		defaultValidation.label = labelAmount;
		defaultValidation.input.element = amount;
		defaultValidation.input.validation.min.message = 'É menor que 1 carectere.';
		defaultValidation.input.validation.max.message = 'É maior que 10 carecteres.';
		validate(event, defaultValidation);
	});
				
	clearError(name, labelName, messages.default.name);
	clearError(password, labelPassword, messages.default.password);
	
	const queryString = window.location.search;
	function handleEditParams(){
		const urlParams = new URLSearchParams(queryString);
		const qsid = urlParams.get("id");
		const qsname = urlParams.get("name");
		const qspassword = urlParams.get("password");
		id.val(qsid);
		name.val(qsname);
		password.val(qspassword);
	}
	if(id.length!=0 && amount.length==0){
		handleEditParams();
	}
	
	function handleAddMoneyParams(){
		const urlParams = new URLSearchParams(queryString);
		const cid = urlParams.get("id");
		const cname = urlParams.get("amount");
		id.val(cid);
		amount.val(cname);
	}
	if(id.length!=0 && amount.length!=0){
		handleAddMoneyParams();
	}
});
/** jquery*/ 


