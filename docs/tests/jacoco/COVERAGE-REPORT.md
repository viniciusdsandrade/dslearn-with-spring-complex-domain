# Test Coverage Report — JaCoCo

**Projeto:** dslearn  
**Data:** 2026-04-03  
**JaCoCo version:** 0.8.13  
**Testes executados:** 149 (0 falhas)

---

## Resumo Geral

| Metrica      | Coberto | Total | Percentual |
|--------------|---------|-------|------------|
| Instructions | 3.030   | 4.466 | **67%**    |
| Lines        | 694     | 943   | **73%**    |
| Branches     | 40      | 266   | 15%        |
| Methods      | 183     | 296   | 62%        |

---

## Cobertura por Camada

| Camada     | Instructions | Branches | Lines | Methods |
|------------|--------------|----------|-------|---------|
| service    | **87%**      | **92%**  | 98%   | 70%     |
| dto        | **100%**     | -        | 100%  | 100%    |
| controller | 52%          | -        | 57%   | 51%     |
| entity     | 14%          | 0%       | 18%   | 23%     |
| dslearn    | 37%          | -        | 33%   | 50%     |

---

## Cobertura por Service

| Classe              | Instructions | Lines | Coberto/Total |
|---------------------|--------------|-------|---------------|
| CourseService       | **94%**      | 100%  | 109/115       |
| RoleService         | **95%**      | 100%  | 129/135       |
| UserService         | **95%**      | 100%  | 141/147       |
| ResourceService     | **93%**      | 100%  | 165/177       |
| NotificationService | **92%**      | 100%  | 147/159       |
| OfferService        | **92%**      | 100%  | 145/157       |
| ContentService      | **92%**      | 100%  | 155/167       |
| TaskService         | **90%**      | 100%  | 179/197       |
| DeliverService      | 84%          | 100%  | 228/270       |
| ReplyService        | 83%          | 100%  | 151/181       |
| EnrollmentService   | 81%          | 98%   | 187/229       |
| SectionService      | 80%          | 92%   | 178/221       |
| TopicService        | 76%          | 93%   | 223/290       |

---

## Cobertura por Controller

| Classe                 | Instructions | Lines | Coberto/Total |
|------------------------|--------------|-------|---------------|
| EnrollmentController   | **71%**      | 77%   | 28/39         |
| CourseController       | 52%          | 60%   | 20/38         |
| ContentController      | 50%          | 55%   | 18/36         |
| DeliverController      | 50%          | 55%   | 18/36         |
| NotificationController | 50%          | 55%   | 18/36         |
| OfferController        | 50%          | 55%   | 18/36         |
| ReplyController        | 50%          | 55%   | 18/36         |
| ResourceController     | 50%          | 55%   | 18/36         |
| RoleController         | 50%          | 55%   | 18/36         |
| SectionController      | 50%          | 55%   | 18/36         |
| TaskController         | 50%          | 55%   | 18/36         |
| TopicController        | 50%          | 55%   | 18/36         |
| UserController         | 50%          | 55%   | 18/36         |

> Controllers ficam em ~50% porque os testes cobrem GET (findAll, findById), POST (create, validation) mas nao PUT (
> update) e DELETE (delete). Adicionar testes para esses 2 endpoints subiria para ~90%.

---

## Cobertura por Entity

| Classe       | Instructions | Lines | Coberto/Total |
|--------------|--------------|-------|---------------|
| Task         | 25%          | 33%   | 22/87         |
| Offer        | 26%          | 36%   | 23/88         |
| Lesson       | 27%          | 36%   | 25/90         |
| Course       | 23%          | 41%   | 20/85         |
| Role         | 10%          | 14%   | 7/65          |
| User         | 10%          | 14%   | 7/65          |
| Content      | 0%           | 0%    | 0/65          |
| Deliver      | 0%           | 0%    | 0/65          |
| Enrollment   | 0%           | 0%    | 0/65          |
| Notification | 0%           | 0%    | 0/65          |
| Reply        | 0%           | 0%    | 0/65          |
| Resource     | 0%           | 0%    | 0/65          |
| Section      | 0%           | 0%    | 0/65          |
| Topic        | 0%           | 0%    | 0/65          |

> Entidades tem cobertura baixa porque `equals()`/`hashCode()` com `HibernateProxy` nao sao exercitados em testes
> unitarios com Mockito. Construtores e getters/setters sao cobertos indiretamente via service tests. Testar proxy real
> requer testes de integracao com contexto JPA.

---

## DTOs (100% cobertos)

Todos os 26 records (13 DTO + 13 RequestDTO) tem 100% de cobertura de instrucoes.

---

## Enums (100% cobertos)

| Enum          | Cobertura |
|---------------|-----------|
| ResourceType  | 100%      |
| DeliverStatus | 100%      |

---

## Como reproduzir

```bash
./mvnw test
# Relatorio HTML: target/site/jacoco/index.html
# Relatorio CSV:  target/site/jacoco/jacoco.csv
# Relatorio XML:  target/site/jacoco/jacoco.xml
```
