import requests

url = "https://minesweeper-amz.herokuapp.com/api/minesweeper/game/1"

headers = {
        "cache-control": "no-cache",
        "Content-Type": "application/json"
}

# data to be sent to api 
json = {"row":"0",
    	"column":"0",
    	"action":"DISCOVER"}

response = requests.post(url, headers=headers, json = json)

print(response.text)