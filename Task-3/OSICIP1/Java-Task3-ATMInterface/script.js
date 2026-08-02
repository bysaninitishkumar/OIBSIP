const accounts = [
  { id: 1001, pin: 1111, balance: 5000 },
  { id: 1002, pin: 2222, balance: 3000 },
  { id: 1003, pin: 3333, balance: 7000 }
];

let currentAccount = null;
let loginAttempts = 0;
let transactionHistory = [];

const loginSection = document.getElementById('loginSection');
const menuSection = document.getElementById('menuSection');
const loginMessage = document.getElementById('loginMessage');
const actionPanel = document.getElementById('actionPanel');
const balanceDisplay = document.getElementById('balanceDisplay');

window.addEventListener('DOMContentLoaded', () => {
  document.getElementById('loginBtn').addEventListener('click', handleLogin);
  document.querySelectorAll('[data-action]').forEach((button) => {
    button.addEventListener('click', () => handleAction(button.dataset.action));
  });
});

function handleLogin() {
  const userId = Number(document.getElementById('userId').value);
  const pin = Number(document.getElementById('pin').value);

  const account = accounts.find(a => a.id === userId && a.pin === pin);

  if (account) {
    currentAccount = account;
    loginAttempts = 0;
    transactionHistory = [];
    loginSection.classList.add('hidden');
    menuSection.classList.remove('hidden');
    balanceDisplay.textContent = `Current Balance: ₹${currentAccount.balance}`;
    loginMessage.textContent = '';
    actionPanel.innerHTML = '<p>Choose an option from the menu.</p>';
  } else {
    loginAttempts++;
    if (loginAttempts >= 3) {
      loginMessage.textContent = 'Access denied. Maximum login attempts exceeded.';
      document.getElementById('loginBtn').disabled = true;
    } else {
      loginMessage.textContent = 'Invalid User ID or PIN. Please try again.';
    }
  }
}

function handleAction(action) {
  if (!currentAccount) return;

  switch (action) {
    case 'history':
      renderHistory();
      break;
    case 'deposit':
      actionPanel.innerHTML = `
        <label>Deposit Amount</label>
        <input id="depositAmount" type="number" min="1" placeholder="500" />
        <button id="confirmDeposit">Confirm Deposit</button>
      `;
      document.getElementById('confirmDeposit').addEventListener('click', () => {
        const amount = Number(document.getElementById('depositAmount').value);
        if (amount > 0) {
          currentAccount.balance += amount;
          transactionHistory.push({ type: 'Deposit', amount, recipient: '-' });
          balanceDisplay.textContent = `Current Balance: ₹${currentAccount.balance}`;
          actionPanel.innerHTML = `<p>Deposit successful.</p>`;
        } else {
          actionPanel.innerHTML = `<p>Invalid amount.</p>`;
        }
      });
      break;
    case 'withdraw':
      actionPanel.innerHTML = `
        <label>Withdraw Amount</label>
        <input id="withdrawAmount" type="number" min="1" placeholder="100" />
        <button id="confirmWithdraw">Confirm Withdraw</button>
      `;
      document.getElementById('confirmWithdraw').addEventListener('click', () => {
        const amount = Number(document.getElementById('withdrawAmount').value);
        if (amount > currentAccount.balance) {
          actionPanel.innerHTML = '<p>Insufficient Funds</p>';
        } else if (amount > 0) {
          currentAccount.balance -= amount;
          transactionHistory.push({ type: 'Withdraw', amount, recipient: '-' });
          balanceDisplay.textContent = `Current Balance: ₹${currentAccount.balance}`;
          actionPanel.innerHTML = `<p>Withdrawal successful.</p>`;
        } else {
          actionPanel.innerHTML = '<p>Invalid amount.</p>';
        }
      });
      break;
    case 'transfer':
      actionPanel.innerHTML = `
        <label>Recipient Account ID</label>
        <input id="recipientId" type="number" placeholder="1002" />
        <label>Transfer Amount</label>
        <input id="transferAmount" type="number" min="1" placeholder="200" />
        <button id="confirmTransfer">Confirm Transfer</button>
      `;
      document.getElementById('confirmTransfer').addEventListener('click', () => {
        const recipientId = Number(document.getElementById('recipientId').value);
        const amount = Number(document.getElementById('transferAmount').value);
        const recipient = accounts.find(acc => acc.id === recipientId);

        if (recipient && amount <= currentAccount.balance && amount > 0) {
          currentAccount.balance -= amount;
          recipient.balance += amount;
          transactionHistory.push({ type: 'Transfer', amount, recipient: recipientId });
          balanceDisplay.textContent = `Current Balance: ₹${currentAccount.balance}`;
          actionPanel.innerHTML = '<p>Transfer successful.</p>';
        } else {
          actionPanel.innerHTML = '<p>Insufficient Funds or invalid recipient account.</p>';
        }
      });
      break;
    case 'quit':
      currentAccount = null;
      menuSection.classList.add('hidden');
      loginSection.classList.remove('hidden');
      loginMessage.textContent = 'Thank you for using our ATM. Have a nice day!';
      document.getElementById('loginBtn').disabled = false;
      document.getElementById('userId').value = '';
      document.getElementById('pin').value = '';
      actionPanel.innerHTML = '';
      balanceDisplay.textContent = '';
      break;
  }
}

function renderHistory() {
  if (transactionHistory.length === 0) {
    actionPanel.innerHTML = '<p>No transactions performed in this session.</p>';
    return;
  }

  const list = transactionHistory
    .map((item) => item.type === 'Transfer'
      ? `${item.type} : ₹${item.amount} to Account ${item.recipient}`
      : `${item.type} : ₹${item.amount}`)
    .join('<br>');

  actionPanel.innerHTML = `<div>${list}</div>`;
}
