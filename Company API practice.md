# GET #obtain a certain specific company with response of id, name
Request:
Get /companies/{id}

Response:
200 OK
{
    "id": 1,
    "name": "Company 1"
}

# GET #obtain list of all employee under a certain specific company
Request:
Get /companies/{id}/employees

Response:
200 OK
[
    {
        "id": 1,
        "name": "Employee 1"
    },
    {
        "id": 2,
        "name": "Employee 2"
    }
]

# GET #Page query, page equals 1, size equals 5, it will return the data in company list from index 0 to index 4
Request:
Get /companies?page=1&size=5

Response:
200 OK
[
    {
        "id": 1,
        "name": "Company 1"
    },
    {
        "id": 2,
        "name": "Company 2"
    },
    {
        "id": 3,
        "name": "Company 3"
    },
    {
        "id": 4,
        "name": "Company 4"
    },
    {
        "id": 5,
        "name": "Company 5"
    }
]

# PUT # update an employee with company
Request:
Put /companies/{id}/employees
{
    "id": 1,
    "name": "Employee 1"
}

Response:
200 OK
{
    "id": 1,
    "name": "Employee 1"
}

# POST # add a new company
Request:
Post /companies
{
    "name": "Company 1"
}

Response:
201 Created
{
    "id": 1,
    "name": "Company 1"
}

# DELETE # delete a company
Request:
Delete /companies/{id}

Response:
204 No Content
{
    "id": 1
}




