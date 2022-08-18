const chatMessage = async (parameters) =>  {
	const baseUrl = window.location.origin;
	const url = new URL(`${window.location.pathname.split('/')[1]}/rest/chatMessage/`, baseUrl);
	return fetch(url.toString(), {
		method: 'POST', 
		headers : new Headers({
 			'Content-Type': 'application/json'
		}),
		body: JSON.stringify({
			
		})
	});
}

const chatMessageForm = (container) => {
	const html = `<form id='chatMessage-form'>
		<button type='button'>Test</button>
	</form>`;

	container.insertAdjacentHTML('beforeend', html)


	container.querySelector('#chatMessage-form button').onclick = () => {
		const params = {

		};

		chatMessage(params).then(r => r.text().then(
				t => alert(t)
			));
	};
}

export { chatMessage, chatMessageForm };