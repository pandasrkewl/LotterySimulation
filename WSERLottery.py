from time import time
from random import randint
from math import log2

class WSERLottery:
    def __init__(self):
        # drawPool is the list of total tickets
        # chosen is the list of chosen participants
        # repeats is the list of tickets that have been drawn but are already in chosen
        # soloCount is the count of tickets of 1 person with that many tickets that got drawn
        # numPeople is the number of people with that many tickets in the year
        self.drawPool = []
        self.chosen = set()
        self.repeats = []
        self.soloCounts = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        #2022:
        #self.numPeople = [3318, 1063, 722, 514, 328, 186, 59, 18]
        #2023:
        self.numPeople = [3560, 1578, 731, 525, 374, 232, 127, 37, 5]
        #2024:
        #self.numPeople = [4434, 2216, 1231, 606, 420, 256, 147, 70, 8]

    def runLottery(self):
        start = time()

        setupStart = time()
        self.setUpTickets()
        setupEnd = time()
        simStart = 0
        simEnd = 0
        resultTime = 0
        finishTime = 0

        numSimulations = 100
        #2022:
        #numDraws = 298
        #2023:
        numDraws = 349
        #2024:
        #numDraws = 345
        for i in range(numSimulations):
            if i == 0:
                simStart = time()
            self.simulation(numDraws)
            if i == 0:
                simEnd = time()
            self.countResults()
            if i == 0:
                resultTime = time()
            self.resetLottery()
            if i == 0:
                finishTime = time()

        print(f'Total: {1000*(time()-start)} milliseconds')
        print(f'    Setup: {1000*(setupEnd-setupStart)} milliseconds')
        print(f'    Simul: {1000*(simEnd-simStart)} milliseconds')
        print(f'    Reslt: {1000*(resultTime-simEnd)} milliseconds')
        print(f'    Reset: {1000*(finishTime-resultTime)} milliseconds')
        return self.soloCounts



    def setUpTickets(self):
        for i in range(len(self.numPeople)):
            numTickets = 2**i
            for j in range(self.numPeople[i]):
                for k in range(numTickets):
                    self.drawPool.append(f'{numTickets}Ticket{j}')


    # Randomly selects tickets from the drawPool, checking if that person has already been chosen
    #   If that person has already been chosen, remove that ticket from drawPool, add it to repeats list, and reroll (decrement and continue)
    #   If that person hasn't already been chosen, remove that ticket from the drawPool and add it to the chosen list
    # Finish simulating once numDraws people have been selected
    def simulation(self, numDraws):
        i = 0
        rand = -1
        
        while i < numDraws:
            rand = randint(0, len(self.drawPool)-1)
            removed = self.drawPool[rand]
            self.drawPool.remove(removed)
            if removed in self.chosen:
                self.repeats.append(removed)
                continue
            self.chosen.add(removed)
            i += 1
    
    def countResults(self):
        for ticket in self.chosen:
            index = ticket.index('Ticket')

            # I only track Ticket1 because I only want to count how many of one type of person is chosen within each ticket group.
            # As a result, I get the probability of one person's chance of being chosen if they have a certain number of tickets.
            if ticket[index:] == 'Ticket1':
                numTicket = int(log2(int(ticket[0:index])))
                self.soloCounts[numTicket] += 1

    # Reset the drawPool by adding in all the chosen/repeated tickets, then clear the chosen/repeated lists
    def resetLottery(self):
        for ticket in self.chosen:
            self.drawPool.append(ticket)
        for ticket in self.repeats:
            self.drawPool.append(ticket)
        self.chosen.clear()
        self.repeats.clear()
        
if __name__ == '__main__':
    lottery = WSERLottery()
    print(lottery.runLottery())






