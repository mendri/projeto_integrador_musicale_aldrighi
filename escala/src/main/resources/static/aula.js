async function salvar() {
	const descricao = document.getElementById('descricao').value;
	const aula = { descricao };
	const response = await fetch('/api/aula', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(aula)
	});

	if (response.ok) {
		fecharModal('modal-nova-aula');
		location.reload();
	} else {
		const { message } = await response.json();
		alert(`Erro ao salvar aula: ${message}`);
	}
}

async function excluirAula(id) {
	const response = await fetch(`/api/aula/${id}`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		}
	});

	if (response.ok) {
		location.reload();
	} else {
		const { message } = await response.json();
		alert(`Erro ao excluir aula: ${message}`);
	}
}
