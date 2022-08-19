const odooMessageAnswer = async (parameters) =>  {
	const baseUrl = window.location.origin;
	const url = new URL(`${window.location.pathname.split('/')[1]}/rest/odooMessageAnswer/`, baseUrl);
	if (parameters.messageId !== undefined) {
		url.searchParams.append('messageId', parameters.messageId);
	}

	return fetch(url.toString(), {
		method: 'GET'
	});
}

const odooMessageAnswerForm = (container) => {
	const html = `<form id='odooMessageAnswer-form'>
		<div id='odooMessageAnswer-parentId-form-field'>
			<label for='parentId'>parentId</label>
			<input type='text' id='odooMessageAnswer-parentId-param' name='parentId'/>
		</div>
		<button type='button'>Test</button>
	</form>`;

	container.insertAdjacentHTML('beforeend', html)

	const parentId = container.querySelector('#odooMessageAnswer-parentId-param');

	container.querySelector('#odooMessageAnswer-form button').onclick = () => {
		const params = {
			parentId : parentId.value !== "" ? parentId.value : undefined
		};

		odooMessageAnswer(params).then(r => r.text().then(
				t => alert(t)
			));
	};
}

export { odooMessageAnswer, odooMessageAnswerForm };