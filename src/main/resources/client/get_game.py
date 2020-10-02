import requests

url = "https://minesweeper-amz.herokuapp.com/api/minesweeper/game/1"

headers = {
        "cache-control": "no-cache",
        "Content-Type": "application/json"
}

response = requests.get(url, headers=headers)

print(response.text)