# Projeto Integrador — Escala de Horários e Alunos

Sistema web para gerir a escala semanal de horários e alunos de uma escola.

**Stack:** Java 25, Spring Boot 4 (Web MVC, Data JPA, Thymeleaf, Validation), Tailwind CSS, JavaScript, SQLite (local) / PostgreSQL (nuvem), JUnit + Mockito, Docker, GitHub Actions.

## Rodando localmente

```bash
cd escala
./mvnw spring-boot:run
```

Acesse http://localhost:8080. Localmente o banco é o arquivo SQLite `escala/escala.db` (criado automaticamente).

## API externa: feriados (BrasilAPI)

A tela de escala consulta `https://brasilapi.com.br/api/feriados/v1/{ano}` e avisa quando um feriado dos próximos 60 dias cai em um dia comercial ativo, mostrando quantas aulas precisam ser remarcadas. Se a API estiver fora do ar, a tela funciona normalmente, só sem o aviso.

O mesmo dado está disponível em JSON: `GET /api/feriados/proximos`.

## Testes

```bash
cd escala
./mvnw test
```

Os testes também rodam a cada push no GitHub Actions (`.github/workflows/ci.yml`).

## Deploy na nuvem (Render + Neon, planos gratuitos)

O app usa o profile `prod` na nuvem, com PostgreSQL. Localmente nada muda (continua SQLite).

### 1. Banco de dados no Neon

1. Crie uma conta em https://neon.tech e um projeto (ex.: `escala`).
2. Na tela de conexão, copie **host**, **banco**, **usuário** e **senha**.
3. Monte a URL JDBC: `jdbc:postgresql://HOST/BANCO?sslmode=require`

### 2. Aplicação no Render

1. Crie uma conta em https://render.com e conecte o GitHub.
2. **New → Web Service**, escolha este repositório e a branch desejada.
3. Em **Language**, selecione **Docker** (o `Dockerfile` está na raiz do repositório). Plano: **Free**.
4. Em **Environment Variables**, adicione:

   | Variável | Valor |
   |---|---|
   | `DATABASE_URL` | URL JDBC montada no passo anterior |
   | `DATABASE_USERNAME` | usuário do Neon |
   | `DATABASE_PASSWORD` | senha do Neon |

5. Clique em **Create Web Service**. A cada `git push` o Render refaz o deploy.

Observações do plano gratuito: o Render "dorme" após alguns minutos sem acessos, e o primeiro acesso depois disso demora cerca de 1 minuto. Abra a página antes de uma apresentação. As tabelas são criadas automaticamente no primeiro start (`ddl-auto=update`), com o banco vazio.
