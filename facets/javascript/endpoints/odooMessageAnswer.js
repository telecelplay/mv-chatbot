const odooMessageAnswer = async (parameters) =>  {
	const baseUrl = window.location.origin;
	const url = new URL(`${window.location.pathname.split('/')[1]}/rest/odooMessageAnswer/${parameters.messageId}`, baseUrl);
	return fetch(url.toString(), {
		method: 'GET'
	});
}

const odooMessageAnswerForm = (container) => {
	const html = `<form id='odooMessageAnswer-form'>
		<div id='odooMessageAnswer-messageId-form-field'>
			<label for='messageId'>messageId</label>
			<input type='text' id='odooMessageAnswer-messageId-param' name='messageId'/>
		</div>
		<button type='button'>Test</button>
	</form>`;

	container.insertAdjacentHTML('beforeend', html)

	const messageId = container.querySelector('#odooMessageAnswer-messageId-param');

	container.querySelector('#odooMessageAnswer-form button').onclick = () => {
		const params = {
			messageId : messageId.value !== "" ? messageId.value : undefined
		};

		odooMessageAnswer(params).then(r => r.text().then(
				t => alert(t)
			));
	};
}

export { odooMessageAnswer, odooMessageAnswerForm };