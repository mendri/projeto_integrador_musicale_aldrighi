async function salvar() {
	const nome = document.getElementById('nome').value;
	const aluno = { nome };
	const response =await fetch('/api/aluno', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(aluno)
	});

	if (response.ok) {
		fecharModal('modal-novo-aluno');
		location.reload();
	} else {
		const { message } = await response.json();
		alert(`Erro ao salvar aluno: ${message}`);
	}
}

async function excluirAluno(id) {
	const response = await fetch(`/api/aluno/${id}`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		}
	});

	if (response.ok) {
		location.reload();
	} else {
		const { message } = await response.json();
		alert(`Erro ao excluir aluno: ${message}`);
	}
}
