---
name: Api Request
about: 최대한 구체적으로 작성해주세요.
title: ''
labels: ''
assignees: ''
---

**Describe**
응답받고자 하는 api에 대해 자세하게 설명해주세요.


**Request**
요청 시 보내는 항목을 입력해주세요.
````
Example
METHOD: POST

HEADERS 
{
    "Authorization": `Bearer ${accessToken}`,
    "Content-Type": multipart/form-data,
    ...
}

PARAMETERS
{
    "id": String,
    "password": String,
	...
}
````
**Response**
응답 받고자 하는 파라미터를 알려주세요.
````
Example
PARAMETERS
{
    "accessToken": String,
    "refreshToken": String,
    ...
}
````

**Additional context**
추가적으로 설명할 부분이 있으면 작성해 주세요.

