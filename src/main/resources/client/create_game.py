import requests

url = "https://minesweeper-amz.herokuapp.com/api/minesweeper/game"

headers = {
        "cache-control": "no-cache",
        "Content-Type": "application/json"
}

# data to be sent to api 
json = {"cant_rows":"4",
        "cant_columns":"4",
        "cant_mines":"2",
        "user_name":"Test"}

response = requests.post(url, headers=headers, json = json)

print(response.text)