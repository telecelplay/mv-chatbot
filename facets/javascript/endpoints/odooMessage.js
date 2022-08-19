const odooMessage = async (parameters) =>  {
	const baseUrl = window.location.origin;
	const url = new URL(`${window.location.pathname.split('/')[1]}/rest/odooMessage/`, baseUrl);
	return fetch(url.toString(), {
		method: 'POST', 
		headers : new Headers({
 			'Content-Type': 'application/json'
		}),
		body: JSON.stringify({
			body : parameters.body,
			null : parameters.null,
			null : parameters.null,
			null : parameters.null,
			parentId : parameters.parentId
		})
	});
}

const odooMessageForm = (container) => {
	const html = `<form id='odooMessage-form'>
		<div id='odooMessage-body-form-field'>
			<label for='body'>body</label>
			<input type='text' id='odooMessage-body-param' name='body'/>
		</div>
		<div id='odooMessage-null-form-field'>
			<label for='null'>null</label>
			<input type='text' id='odooMessage-null-param' name='null'/>
		</div>
		<div id='odooMessage-null-form-field'>
			<label for='null'>null</label>
			<input type='text' id='odooMessage-null-param' name='null'/>
		</div>
		<div id='odooMessage-null-form-field'>
			<label for='null'>null</label>
			<input type='text' id='odooMessage-null-param' name='null'/>
		</div>
		<div id='odooMessage-parentId-form-field'>
			<label for='parentId'>parentId</label>
			<input type='text' id='odooMessage-parentId-param' name='parentId'/>
		</div>
		<button type='button'>Test</button>
	</form>`;

	container.insertAdjacentHTML('beforeend', html)

	const body = container.querySelector('#odooMessage-body-param');
	const null = container.querySelector('#odooMessage-null-param');
	const null = container.querySelector('#odooMessage-null-param');
	const null = container.querySelector('#odooMessage-null-param');
	const parentId = container.querySelector('#odooMessage-parentId-param');

	container.querySelector('#odooMessage-form button').onclick = () => {
		const params = {
			body : body.value !== "" ? body.value : undefined,
			null : null.value !== "" ? null.value : undefined,
			null : null.value !== "" ? null.value : undefined,
			null : null.value !== "" ? null.value : undefined,
			parentId : parentId.value !== "" ? parentId.value : undefined
		};

		odooMessage(params).then(r => r.text().then(
				t => alert(t)
			));
	};
}

export { odooMessage, odooMessageForm };