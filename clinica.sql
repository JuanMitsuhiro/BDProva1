CREATE DATABASE clinica
GO
USE clinica
GO
CREATE TABLE Cliente(
rg			CHAR(9)			NOT NULL,
nome		VARCHAR(45)		NOT NULL,
nascimento	DATE			NOT NULL,
telefone	CHAR(11)		NOT NULL,
senha		CHAR(8)			NOT NULL

PRIMARY KEY(rg)
)
GO
CREATE TABLE Especialidade(
codigo		INT				NOT NULL,
nome		VARCHAR(75)		NOT NULL

PRIMARY KEY(codigo)
)
GO
CREATE TABLE Medico(
rg				CHAR(9)			NOT NULL,
nome			VARCHAR(45)		NOT NULL,
telefone		CHAR(11)		NOT NULL,
especialidade	INT				NOT NULL,
turno			VARCHAR(10)		NOT NULL

PRIMARY KEY(rg)
FOREIGN KEY(especialidade) REFERENCES Especialidade(codigo)
)
GO
CREATE TABLE Consulta(
codigo			INT				IDENTITY(100,1),
cliente			CHAR(9)			NOT NULL,
especialidade	INT				NOT NULL,
medico			CHAR(9)			NOT NULL,
data			DATETIME		NOT NULL,
hora			TIME			NOT NULL,
tipo			VARCHAR(10)		NOT NULL,
categoria		VARCHAR(10)		NOT NULL

PRIMARY KEY(codigo)
FOREIGN KEY(cliente) REFERENCES Cliente(rg),
FOREIGN KEY(medico) REFERENCES Medico(rg),
FOREIGN KEY(especialidade) REFERENCES Especialidade(codigo)
)
GO
INSERT INTO Especialidade VALUES  
(1, 'Cardiologia'),
(2, 'Dermatologia'),
(3, 'Pediatria'),
(4, 'Ortopedia'),
(5, 'Ginecologia'),
(6, 'Neurologia'),
(7, 'Psiquiatria'),
(8, 'Oftalmologia'),
(9, 'Endocrinologia'),
(10, 'Oncologia'),
(11, 'Reumatologia'),
(12, 'Otorrinolaringologia'),
(13, 'Urologia'),
(14, 'Gastroenterologia'),
(15, 'Nefrologia'),
(16, 'Hematologia'),
(17, 'Infectologia'),
(18, 'Medicina do Trabalho'),
(19, 'Medicina de Família e Comunidade'),
(20, 'Cirurgia Geral')
GO
CREATE PROCEDURE sp_validaidade(@datanasc DATE, @valida BIT OUTPUT)
AS
	DECLARE @idade	INT
	SET @idade = (SELECT DATEDIFF(DAY, @datanasc, GETDATE())) / 365

	IF (@idade >= 18)
	BEGIN
		SET @valida = 1
	END
	ELSE
	BEGIN
		SET @valida = 0
	END
GO
CREATE PROCEDURE sp_validarg(@rg CHAR(9), @valida BIT OUTPUT)
AS
BEGIN
	DECLARE @ctd INT = 1,
			@digito INT = 0

	WHILE(@ctd <= 8)
	BEGIN
		SET @digito = @digito + (CAST(SUBSTRING(@rg, @ctd, 1) AS INT)*(10 - @ctd))
		SET @ctd = @ctd + 1
	END

	SET @digito = @digito % 11

	IF (RIGHT(@rg, 1) LIKE '[A-Za-z]' OR @digito > 9)
	BEGIN
		IF (@digito = 10 AND RIGHT(@rg, 1) = 'X')
			SET @valida = 1
		ELSE
			SET @valida = 0
	END
	ELSE IF (RIGHT(@rg, 1) LIKE '[0-9]' AND @digito = (CAST(RIGHT(@rg, 1) AS INT)) AND @rg NOT IN (
        '000000000', '111111111', '222222222', '333333333', '444444444',
        '555555555', '666666666', '777777777', '888888888', '999999999'
    ))
	BEGIN
		SET @valida =  1
	END
	ELSE
	BEGIN
		SET @valida = 0
	END
END
GO
CREATE PROCEDURE sp_validasenha(@senha char(8), @valida BIT OUTPUT)
AS
BEGIN
	IF (@senha LIKE '%[0-9]%' AND LEN(@senha) = 8)
		SET @valida = 1
	ELSE
		SET @valida = 0
END
GO
CREATE PROCEDURE sp_validalogin(@rg CHAR(9),@senha CHAR(8), @saida BIT OUTPUT)
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Cliente WHERE rg = @rg AND senha = @senha)
    BEGIN
        SET @saida = 1
    END
    ELSE
    BEGIN
        SET @saida = 0
    END
END
GO
CREATE PROCEDURE sp_cadastracliente(	@op CHAR(1), @rg CHAR(9),
										@nome VARCHAR(45), @nascimento DATE, 
										@tel CHAR(11), @senha CHAR(8), 
										@saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    DECLARE @idadeValida BIT,
            @senhaValida BIT,
            @rgValida BIT

    BEGIN TRY
        EXEC sp_validaidade @nascimento, @idadeValida OUTPUT
        EXEC sp_validasenha @senha, @senhaValida OUTPUT
        EXEC sp_validarg @rg, @rgValida OUTPUT

        IF @rgValida = 0
            RAISERROR('RG inválido', 16, 1)
        IF @idadeValida = 0
            RAISERROR('Idade inválida', 16, 1)
        IF @senhaValida = 0
            RAISERROR('Senha inválida', 16, 1)

        IF @op = 'I'
        BEGIN
            INSERT INTO Cliente VALUES (@rg, @nome, @nascimento, @tel, @senha)
            SET @saida = 'Cliente inserido com sucesso!'
        END
        ELSE IF @op = 'A'
        BEGIN
            UPDATE Cliente
            SET nascimento = @nascimento, telefone = @tel, senha = @senha
            WHERE rg = @rg
            
            IF @@ROWCOUNT = 0
                RAISERROR('Cliente não encontrado', 16, 1)
            ELSE
                SET @saida = 'Cliente atualizado com sucesso!'
        END
        ELSE
        BEGIN
            RAISERROR('Operação inválida', 16, 1)
        END
        
    END TRY
    BEGIN CATCH
        SET @saida = ERROR_MESSAGE()
    END CATCH
END
GO
CREATE PROCEDURE sp_cadastramedico(@op CHAR(1), @rg CHAR(9), @nome VARCHAR(45),
								   @tel CHAR(11), @especialidade INT,  
								   @turno VARCHAR(10), @saida VARCHAR(100) OUTPUT)
AS

DECLARE @rgValida BIT
EXEC sp_validarg @rg, @rgValida OUTPUT


	IF(@rgValida = 1 )
	BEGIN TRY
		BEGIN
			IF (UPPER(@op) = 'I')
			BEGIN
				INSERT INTO Medico VALUES
				(@rg, @nome, @tel, @especialidade, @turno)
				SET @saida = 'Médico inserido com sucesso!'
			END
			ELSE
			IF(UPPER(@op) = 'A')
			BEGIN
				IF(SELECT nome FROM Medico WHERE rg = @rg) IS NOT NULL
				BEGIN
					UPDATE Medico
					SET telefone = @tel
					WHERE rg = @rg
					SET @saida = 'Médico atualizado com sucesso!'
				END
				ELSE
					RAISERROR('Médico não encontrado', 16, 1)
			END
			ELSE
			IF(UPPER(@op) = 'D')
				BEGIN
					DELETE Medico WHERE rg = @rg
					SET @saida = 'Médico RG '+CAST(@rg AS VARCHAR(9)) + ' excluído com sucesso!'
				END
			ELSE
			BEGIN
				RAISERROR('Operação inválida', 16, 1)
			END
		END
		END TRY
		BEGIN CATCH
				IF(@saida LIKE '%PRIMARY%')
				BEGIN
					RAISERROR('RG DE MÉDICO JA EXISTENTE.', 16, 1)
				END
		END CATCH
	ELSE
	BEGIN
		IF(@rgValida = 0)
			RAISERROR('RG inválido', 16, 1)
		ELSE
			RAISERROR('Operação inválida', 16, 1)
	END
GO
CREATE PROCEDURE sp_cadastraespecialidade(	@op CHAR(1), @codigo INT, @nome VARCHAR(75), 
											@saida VARCHAR(100) OUTPUT)
AS	
	IF (UPPER(@op) IN ('I', 'A') AND (@nome IS NULL OR LTRIM(RTRIM(@nome)) = ''))
    BEGIN
        RAISERROR('Nome da especialidade é obrigatório', 16, 1)
        RETURN
    END
	BEGIN TRY
	BEGIN
		IF(UPPER(@op) = 'D')
		BEGIN
			DELETE Especialidade WHERE codigo = @codigo
			SET @saida = 'Especialidade '+ @nome + ' excluído com sucesso!'
		END
		ELSE IF (UPPER(@op) = 'A')
		BEGIN
			UPDATE Especialidade
			SET nome = @nome
			WHERE codigo = @codigo
			SET @saida = 'Especialidade ' + @nome + ' modificado(a) com sucesso!'
		END
		ELSE IF (UPPER(@op) = 'I')
		BEGIN
			INSERT INTO Especialidade VALUES
			(@codigo, @nome)
			SET @saida ='Especialidade ' + @nome + ' inserida com sucesso!'
		END
	END
	END TRY
	BEGIN CATCH
		SET @saida = ERROR_MESSAGE()
		IF(@saida LIKE '%PRIMARY%')
		BEGIN
			RAISERROR('CODIGO DE ESPECIALIDADE JA EXISTENTE.', 16, 1)
		END
	END CATCH
GO
CREATE PROCEDURE sp_validaRetorno(@rg CHAR(9), @data DATETIME, @hora TIME, @especialidade INT, @valida BIT OUTPUT)
AS
BEGIN 
	DECLARE @ultimaData DATETIME,
			@ultimaTipo VARCHAR(10),
			@ultimaEspecialidade INT

	SET @ultimaTipo = (SELECT TOP 1 tipo FROM Consulta WHERE cliente = @rg ORDER BY data DESC)
	SET @ultimaData = (SELECT TOP 1 data FROM Consulta WHERE cliente = @rg ORDER BY data DESC)
	SET @ultimaEspecialidade = (SELECT TOP 1 especialidade FROM Consulta WHERE cliente = @rg ORDER BY data DESC)

	IF (DATEDIFF(DAY, @ultimaData, @data) < 30 AND UPPER(@ultimaTipo) = 'CONSULTA' AND @ultimaEspecialidade = @especialidade)
	BEGIN
		SET @valida = 1
	END
	ELSE IF (@ultimaData IS NULL OR UPPER(@ultimaTipo) = 'RETORNO')
	BEGIN
		SET @valida = 0
	END
	ELSE IF (DATEDIFF(DAY, GETDATE(), @data) <= 0)
	BEGIN
		RAISERROR('Data inválida', 16, 1)
	END
END

GO
CREATE PROCEDURE sp_cadastraconsulta (	@op CHAR(1), @cliente CHAR(9), 
										@especialidade INT, @data DATETIME, @hora TIME, 
										@tipo VARCHAR(10), @categoria VARCHAR(10),
										@medico CHAR(9), @codigo INT OUTPUT, @saida VARCHAR(100) OUTPUT)
AS
BEGIN
	IF (UPPER(@op) = 'I')
	BEGIN
		DECLARE @retorno BIT

		EXEC sp_validaRetorno @cliente, @data, @hora, @especialidade, @retorno OUTPUT
		IF (@retorno = 1)
			SET @tipo = 'RETORNO'
		ELSE
			SET @tipo = 'CONSULTA'

		INSERT INTO Consulta (cliente, especialidade, data, hora, tipo, categoria, medico) VALUES 
							 (@cliente, @especialidade, @data, @hora, @tipo, @categoria, @medico)
		SELECT @codigo = SCOPE_IDENTITY()
		SET @saida = 'Consulta agendada!'
	END
	ELSE IF (UPPER(@op) = 'A')
	BEGIN
		UPDATE Consulta
		SET medico = @medico
		WHERE codigo = @codigo

		SET @saida = 'Consulta atualizada com sucesso!'
	END
	ELSE IF UPPER(@op) = 'D'
	BEGIN
		DELETE Consulta WHERE codigo = @codigo
		SET @saida = 'Consulta cancelada com sucesso!'
	END
END
GO
